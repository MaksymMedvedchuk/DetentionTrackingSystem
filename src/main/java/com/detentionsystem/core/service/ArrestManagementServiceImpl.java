package com.detentionsystem.core.service;

import com.detentionsystem.core.converter.ExternalDataConverter;
import com.detentionsystem.core.domain.dto.ArrestRequestDto;
import com.detentionsystem.core.domain.dto.ResponseDto;
import com.detentionsystem.core.domain.entity.Arrest;
import com.detentionsystem.core.domain.entity.Person;
import com.detentionsystem.core.exception.ArrestNotFoundException;
import com.detentionsystem.core.repository.ArrestRepository;
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
public class ArrestManagementServiceImpl implements ArrestManagementService {

	private final PersonRepository personRepository;
	private final ArrestRepository arrestRepository;
	private final OrganCodeMatchValidator organCodeMatchValidator;
	private final ExternalDataConverter externalDataConverter;

	public ArrestManagementServiceImpl(
		final PersonRepository personRepository,
		final ArrestRepository arrestRepository,
		final OrganCodeMatchValidator organCodeMatchValidator,
		final ExternalDataConverter externalDataConverter
	) {
		this.personRepository = personRepository;
		this.arrestRepository = arrestRepository;
		this.organCodeMatchValidator = organCodeMatchValidator;
		this.externalDataConverter = externalDataConverter;
	}

	@Transactional
	public ResponseDto processRequest(final ArrestRequestDto request) {
		organCodeMatchValidator.validateOrganCodeMatch(request);
		final OperationType operationType = request.getArrestDto().getOperation();
        if (operationType == OperationType.PRIMARY) {
            return processPrimary(request);
        }
        if (operationType == OperationType.CHANGED) {
            return processChanged(request);
        }
		return processCanceled(request);
	}

	private ResponseDto processPrimary(final ArrestRequestDto request) {
		final Person person = saveOrFindPerson(request);
		final Arrest arrest = convertArrestToEntity(request, person);
		final Arrest savedArrest = arrestRepository.save(arrest);
		return buildResponseDto(savedArrest);
	}

	private Arrest convertArrestToEntity(final ArrestRequestDto request, final Person person) {
		return Arrest.builder()
			.organizationCode(request.getOrganCode())
			.docDate(request.getArrestDto().getDocDate())
			.docNum(request.getArrestDto().getDocNum())
			.purpose(request.getArrestDto().getPurpose())
			.amount(request.getArrestDto().getAmount())
			.statusType(StatusType.ACTIVE)
			.person(person)
			.build();
	}

	private ResponseDto processChanged(final ArrestRequestDto request) {
		final Arrest arrest = arrestRepository.findByDocNum(request.getArrestDto().getRefDocNum())
			.orElseThrow(() -> new ArrestNotFoundException("Arrest not found"));
		final Long amount = arrest.getAmount() - request.getArrestDto().getAmount();
		arrest.setAmount(amount);
		arrest.setPurpose(request.getArrestDto().getPurpose());
		arrest.setStatusType(arrest.getAmount() > 0 ? StatusType.ACTIVE : StatusType.CANCELED);
		final Arrest savedArrest = arrestRepository.save(arrest);
		return buildResponseDto(savedArrest);
	}

	private ResponseDto buildResponseDto(final Arrest arrest) {
		final ResponseDto responseDto = new ResponseDto();
		responseDto.setArrestId(arrest.getId());
		responseDto.setResultCode(ResultCode.SUCCESS);
		return responseDto;
	}

	private ResponseDto processCanceled(final ArrestRequestDto request) {
		final Arrest arrest = arrestRepository.findByDocNum(request.getArrestDto().getRefDocNum())
			.orElseThrow(() -> new ArrestNotFoundException("Arrest not found"));
		arrest.setStatusType(StatusType.CANCELED);
		final Arrest savedArrest = arrestRepository.save(arrest);
		return buildResponseDto(savedArrest);
	}

	private Person saveOrFindPerson(final ArrestRequestDto request) {
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
		final ArrestRequestDto request,
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







