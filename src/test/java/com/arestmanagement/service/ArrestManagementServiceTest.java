package com.arestmanagement.service;

import com.arestmanagement.converter.ExternalDataConverterImpl;
import com.arestmanagement.dto.ArrestDto;
import com.arestmanagement.dto.ArrestRequestDto;
import com.arestmanagement.dto.IdentityDocumentDto;
import com.arestmanagement.dto.ResponseDto;
import com.arestmanagement.entity.Arrest;
import com.arestmanagement.entity.Person;
import com.arestmanagement.exception.ArrestNotFoundException;
import com.arestmanagement.repository.ArrestRepository;
import com.arestmanagement.repository.PersonRepository;
import com.arestmanagement.util.*;
import com.arestmanagement.validator.OrganCodeMatchValidator;
import javafx.util.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrestManagementServiceTest {

    @InjectMocks
    private ArrestManagementServiceImpl arrestManagementService;
    @Mock
    private ExternalDataConverterImpl mockExternalDataConverter;
    @Mock
    private PersonRepository mockPersonRepository;
    @Mock
    private ArrestRepository mockArrestRepository;
    @Mock
    private OrganCodeMatchValidator mockOrganCodeMatchValidator;

    @Test
    public void testProcessPrimary() {
        ArrestRequestDto request = createRequest(1200L, OperationType.PRIMARY);
        Person expectedPerson = createPerson();
        Long expectedAmount = 1200L;
        Arrest expectedArrest = createArrest(expectedPerson, expectedAmount);
        String expectedInternalDocNum = "666666 99 11";
        when(mockExternalDataConverter.convertExternalToInternalData(request))
                .thenReturn(new Pair<>(InternalIdentityDocumentType.PASSPORT, expectedInternalDocNum));
        when(mockPersonRepository.findPerson(request.getFirstName(), request.getLastName(), InternalIdentityDocumentType.PASSPORT,
                expectedInternalDocNum)).thenReturn(Optional.of(expectedPerson));
        when(mockPersonRepository.save(expectedPerson)).thenReturn(expectedPerson);
        when(mockArrestRepository.save(expectedArrest)).thenReturn(expectedArrest);
        ResponseDto expectedResponse = createResponse(expectedArrest);
        ResponseDto actualResponse = arrestManagementService.processRequest(request);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void processChangedToActiveStatus() {
        ArrestRequestDto request = createRequest(1500L, OperationType.CHANGED);
        Arrest arrest = createArrest(2000L);
        when(mockArrestRepository.save(arrest)).thenReturn(arrest);
        when(mockArrestRepository.findByDocNum(request.getArrestDto().getRefDocNum())).thenReturn(Optional.of(arrest));
        ResponseDto actualResponse = arrestManagementService.processRequest(request);
        ResponseDto expectedResponse = createResponse(arrest);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void processChangedToActiveStatusIfArrestEmpty() {
        ArrestRequestDto request = createRequest(1500L, OperationType.CHANGED);
        Arrest arrest = createArrest(2000L);
        when(mockArrestRepository.save(arrest)).thenReturn(arrest);
        when(mockArrestRepository.findByDocNum(request.getArrestDto().getRefDocNum())).thenReturn(Optional.empty());
        assertThrows(ArrestNotFoundException.class, () -> arrestManagementService.processRequest(request));
    }

    @Test
    public void processChangedToCanceledStatus() {
        ArrestRequestDto request = createRequest(1500L, OperationType.CHANGED);
        Arrest arrest = createArrest(200L);
        when(mockArrestRepository.findByDocNum(request.getArrestDto().getRefDocNum())).thenReturn(Optional.of(arrest));
        when(mockArrestRepository.save(arrest)).thenReturn(arrest);
        ResponseDto actualResponse = arrestManagementService.processRequest(request);
        ResponseDto expectedResponse = createResponse(arrest);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testProcessCanceled() {
        ArrestRequestDto request = createRequest(1500L, OperationType.CANCELED);
        Arrest arrest = createArrest(1200L);
        when(mockArrestRepository.findByDocNum(request.getArrestDto().getRefDocNum())).thenReturn(Optional.of(arrest));
        when(mockArrestRepository.save(arrest)).thenReturn(arrest);
        ResponseDto expectedResponse = createResponse(arrest);
        ResponseDto actualResponse = arrestManagementService.processRequest(request);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testConvertPersonToEntity() {
        ArrestRequestDto request = new ArrestRequestDto();
        request.setFirstName("Maks");
        request.setLastName("Medvedchuk");
        String internalNumSeries = "666666 99 11";
        InternalIdentityDocumentType internalDocType = InternalIdentityDocumentType.PASSPORT;
        Person expectedPerson = createPerson();
        Person actualPerson = arrestManagementService.convertPersonToEntity(request, internalNumSeries, internalDocType);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void testConvertPersonToEntityDataMismatch() {
        ArrestRequestDto request = new ArrestRequestDto();
        request.setFirstName("Mak");
        request.setLastName("Medvedchuk");
        String internalNumSeries = "666666 99 11";
        InternalIdentityDocumentType internalDocType = InternalIdentityDocumentType.PASSPORT;
        Person expectedPerson = createPerson();
        Person actualPerson = arrestManagementService.convertPersonToEntity(request, internalNumSeries, internalDocType);
        assertNotEquals(expectedPerson, actualPerson);
    }

    private ResponseDto createResponse(Arrest actualArrest) {
        ResponseDto expectedResponse = new ResponseDto();
        expectedResponse.setArrestId(actualArrest.getId());
        expectedResponse.setResultCode(ResultCode.SUCCESS);
        return expectedResponse;
    }

    private Arrest createArrest(Person person, Long amount) {
        return Arrest.builder()
                .organizationCode(OrganizationCode.SERVICE_OF_BAILIFFS)
                .docDate(LocalDate.of(2022, 10, 12))
                .docNum("yu544-1567456")
                .purpose("purpose")
                .amount(amount)
                .person(person)
                .statusType(StatusType.ACTIVE)
                .build();
    }

    private Arrest createArrest(Long amount) {
        return createArrest(null, amount);
    }

    private Person createPerson() {
        return Person.builder()
                .firstName("Maks")
                .lastName("Medvedchuk")
                .identDocType(InternalIdentityDocumentType.PASSPORT)
                .docNumberSeries("666666 99 11")
                .build();
    }

    private ArrestRequestDto createRequest(Long amount, OperationType operationType) {
        return ArrestRequestDto.builder()
                .requestId(UUID.randomUUID())
                .firstName("Maks")
                .lastName("Medvedchuk")
                .organCode(OrganizationCode.SERVICE_OF_BAILIFFS)
                .identityDocumentDto(IdentityDocumentDto.builder()
                        .type(ExternalIdentityDocumentType.SERVICE_OF_BAILIFFS_PASSPORT)
                        .numberSeries("666666-9911")
                        .issueDate(LocalDate.of(2022, 10, 1))
                        .build())
                .arrestDto(ArrestDto.builder()
                        .docDate(LocalDate.of(2022, 10, 12))
                        .docNum("yu544-1567456")
                        .purpose("purpose")
                        .amount(amount)
                        .refDocNum("yu544-1567456_1")
                        .operation(operationType)
                        .build())
                .build();
    }
}
