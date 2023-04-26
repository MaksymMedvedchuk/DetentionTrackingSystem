package com.arestmanagement.service;

import com.arestmanagement.converter.ExternalDataConverter;
import com.arestmanagement.dto.ArrestRequestDto;
import com.arestmanagement.dto.ResponseDto;
import com.arestmanagement.entity.Arrest;
import com.arestmanagement.entity.Person;
import com.arestmanagement.exception.ArrestNotFoundException;
import com.arestmanagement.repository.ArrestRepository;
import com.arestmanagement.repository.PersonRepository;
import com.arestmanagement.util.InternalIdentityDocumentType;
import com.arestmanagement.util.OperationType;
import com.arestmanagement.util.ResultCode;
import com.arestmanagement.util.StatusType;
import com.arestmanagement.validator.OrganCodeMatchValidator;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArrestManagementServiceImpl implements ArrestManagementService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ArrestRepository arrestRepository;
    @Autowired
    private OrganCodeMatchValidator organCodeMatchValidator;
    @Autowired
    private ExternalDataConverter externalDataConverter;

    @Transactional
    public ResponseDto processRequest(ArrestRequestDto request) {
        organCodeMatchValidator.validateOrganCodeMatch(request);
        OperationType operationType = request.getArrestDto().getOperation();
        if (operationType == OperationType.PRIMARY) return processPrimary(request);
        else if (operationType == OperationType.CHANGED) return processChanged(request);
        else return processCanceled(request);
    }

    private ResponseDto processPrimary(ArrestRequestDto request) {
        Person person = saveOrFindPerson(request);
        Arrest arrest = convertArrestToEntity(request, person);
        arrest = arrestRepository.save(arrest);
        return buildResponseDto(arrest);
    }

    Arrest convertArrestToEntity(ArrestRequestDto request, Person person) {
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

    private ResponseDto processChanged(ArrestRequestDto request) {
        Arrest arrest = arrestRepository.findByDocNum(request.getArrestDto().getRefDocNum())
                .orElseThrow(() -> new ArrestNotFoundException("Arrest not found"));
        Long amount = arrest.getAmount() - request.getArrestDto().getAmount();
        arrest.setAmount(amount);
        arrest.setPurpose(request.getArrestDto().getPurpose());
        arrest.setStatusType(arrest.getAmount() > 0 ? StatusType.ACTIVE : StatusType.CANCELED);
        arrest = arrestRepository.save(arrest);
        return buildResponseDto(arrest);
    }

    private ResponseDto buildResponseDto(Arrest updateArrest) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setArrestId(updateArrest.getId());
        responseDto.setResultCode(ResultCode.SUCCESS);
        return responseDto;
    }

    private ResponseDto processCanceled(ArrestRequestDto request) {
        Arrest arrest = arrestRepository.findByDocNum(request.getArrestDto().getRefDocNum())
                .orElseThrow(() -> new ArrestNotFoundException("Arrest not found"));
        arrest.setStatusType(StatusType.CANCELED);
        arrest = arrestRepository.save(arrest);
        return buildResponseDto(arrest);
    }

    Person saveOrFindPerson(ArrestRequestDto request) {
        Pair<InternalIdentityDocumentType, String> internalIdentDoc = externalDataConverter.convertExternalToInternalData(request);
        String internalNumSeries = internalIdentDoc.getValue();
        InternalIdentityDocumentType internalDocType = internalIdentDoc.getKey();
        Person person = personRepository.findPerson(request.getFirstName(), request.getLastName(),
                        internalDocType, internalNumSeries)
                .orElse(convertPersonToEntity(request, internalNumSeries, internalDocType));
        return personRepository.save(person);
    }

    //todo to test make private method package private and create test in the same package in test directory
    Person convertPersonToEntity(ArrestRequestDto request, String internalNumSeries, InternalIdentityDocumentType internalDocType) {
        return Person.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .identDocType(internalDocType)
                .docNumberSeries(internalNumSeries)
                .build();
    }
}

//todo JUnit, Mockito (spring-boot-test-starter)






