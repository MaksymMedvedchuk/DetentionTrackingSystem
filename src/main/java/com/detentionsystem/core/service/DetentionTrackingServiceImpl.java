package com.detentionsystem.core.service;

import com.detentionsystem.core.converter.ExternalDataConverter;
import com.detentionsystem.core.domain.dto.DetentionRequestDto;
import com.detentionsystem.core.domain.dto.ResponseDto;
import com.detentionsystem.core.domain.entity.Detention;
import com.detentionsystem.core.domain.entity.Person;
import com.detentionsystem.core.exception.DetentionNotFoundException;
import com.detentionsystem.core.repository.DetentionRepository;
import com.detentionsystem.core.domain.enums.InternalIdentityDocumentType;
import com.detentionsystem.core.domain.enums.OperationType;
import com.detentionsystem.core.repository.PersonRepository;
import com.detentionsystem.util.Pair;
import com.detentionsystem.core.domain.enums.ResultCode;
import com.detentionsystem.core.domain.enums.StatusType;
import com.detentionsystem.validator.OrganCodeMatchValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetentionTrackingServiceImpl implements DetentionTrackingService {

	private final PersonRepository personRepository;
	private final DetentionRepository detentionRepository;
	private final OrganCodeMatchValidator organCodeMatchValidator;
	private final ExternalDataConverter externalDataConverter;

	public DetentionTrackingServiceImpl(
		final PersonRepository personRepository,
		final DetentionRepository detentionRepository,
		final OrganCodeMatchValidator organCodeMatchValidator,
		final ExternalDataConverter externalDataConverter
	) {
		this.personRepository = personRepository;
		this.detentionRepository = detentionRepository;
		this.organCodeMatchValidator = organCodeMatchValidator;
		this.externalDataConverter = externalDataConverter;
	}

	@Transactional
	public ResponseDto processRequest(final DetentionRequestDto request) {
		organCodeMatchValidator.validateOrganCodeMatch(request);
		final OperationType operationType = request.getDetentionDto().getOperation();
        if (operationType == OperationType.PRIMARY) {
            return processPrimary(request);
        }
        if (operationType == OperationType.CHANGED) {
            return processChanged(request);
        }
		return processCanceled(request);
	}

	private ResponseDto processPrimary(final DetentionRequestDto request) {
		final Person person = saveOrFindPerson(request);
		final Detention detention = convertArrestToEntity(request, person);
		final Detention savedDetention = detentionRepository.save(detention);
		return buildResponseDto(savedDetention);
	}

	private Detention convertArrestToEntity(final DetentionRequestDto request, final Person person) {
		return Detention.builder()
			.organizationCode(request.getOrganCode())
			.docDate(request.getDetentionDto().getDocDate())
			.docNum(request.getDetentionDto().getDocNum())
			.purpose(request.getDetentionDto().getPurpose())
			.amount(request.getDetentionDto().getAmount())
			.statusType(StatusType.ACTIVE)
			.person(person)
			.build();
	}

	private ResponseDto processChanged(final DetentionRequestDto request) {
		final Detention detention = detentionRepository.findByDocNum(request.getDetentionDto().getDocNum())
			.orElseThrow(() -> new DetentionNotFoundException("Detention not found"));
		final Long amount = detention.getAmount() - request.getDetentionDto().getAmount();
		detention.setAmount(amount);
		detention.setPurpose(request.getDetentionDto().getPurpose());
		detention.setStatusType(detention.getAmount() > 0 ? StatusType.ACTIVE : StatusType.CANCELED);
		final Detention savedDetention = detentionRepository.save(detention);
		return buildResponseDto(savedDetention);
	}

	private ResponseDto buildResponseDto(final Detention detention) {
		final ResponseDto responseDto = new ResponseDto();
		responseDto.setArrestId(detention.getId());
		responseDto.setResultCode(ResultCode.SUCCESS);
		return responseDto;
	}

	private ResponseDto processCanceled(final DetentionRequestDto request) {
		final Detention detention = detentionRepository.findByDocNum(request.getDetentionDto().getDocNum())
			.orElseThrow(() -> new DetentionNotFoundException("Detention not found"));
		detention.setStatusType(StatusType.CANCELED);
		final Detention savedDetention = detentionRepository.save(detention);
		return buildResponseDto(savedDetention);
	}

	private Person saveOrFindPerson(final DetentionRequestDto request) {
		final Pair<InternalIdentityDocumentType, String>
			internalIdentDoc =
			externalDataConverter.convertExternalToInternalData(request);
		final String internalNumSeries = internalIdentDoc.getValue();
		final InternalIdentityDocumentType internalDocType = internalIdentDoc.getKey();
		final Person person = personRepository.findPerson(request.getFirstName(), request.getLastName(),
				internalDocType, internalNumSeries
			)
			.orElse(convertPersonToEntity(request, internalNumSeries, internalDocType));
		return personRepository.save(person);
	}

	Person convertPersonToEntity(
		final DetentionRequestDto request,
		final String internalNumSeries,
		final InternalIdentityDocumentType internalDocType
	) {
		return Person.builder()
			.firstName(request.getFirstName())
			.lastName(request.getLastName())
			.identDocType(internalDocType)
			.docNumberSeries(internalNumSeries)
			.email(request.getEmail())
			.build();
	}
}







