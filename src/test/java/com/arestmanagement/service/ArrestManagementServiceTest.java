package com.arestmanagement.service;

import com.arestmanagement.converter.ExternalDataConverter;
import com.arestmanagement.converter.ExternalDataConverterImpl;
import com.arestmanagement.dto.ArrestDto;
import com.arestmanagement.dto.ArrestRequestDto;
import com.arestmanagement.dto.IdentityDocumentDto;
import com.arestmanagement.entity.Arrest;
import com.arestmanagement.entity.Person;
import com.arestmanagement.repository.PersonRepository;
import com.arestmanagement.util.*;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)//ініціалізація об'єктів мок
public class ArrestManagementServiceTest {


    @InjectMocks
    private ArrestManagementServiceImpl arrestManagementService;

    @Mock
    //todo try to use not mock but real ExternalDataConverterImpl dependency of ArrestManagementServiceImpl
    private ExternalDataConverterImpl externalDataConverter;

    @Mock
    private PersonRepository personRepository;



    @Test
    public void testProcessPrimary() {
        ArrestRequestDto request = ArrestRequestDto.builder()
                .requestId(UUID.randomUUID())
                .firstName("Igor")
                .lastName("Frank")
                .organCode(OrganizationCode.SERVICE_OF_BAILIFFS)
                .identityDocumentDto(IdentityDocumentDto.builder()
                        .type(ExternalIdentityDocumentType.SERVICE_OF_BAILIFFS_PASSPORT)
                        .numberSeries("666666-9911")
                        .issueDate(LocalDate.of(2022, 10, 1))
                        .build())
                .arrestDto(ArrestDto.builder()
                        .docDate(LocalDate.of(2022, 10, 12))
                        .docNum("yu544-1567456")
                        .purpose("Arrest 2")
                        .amount(1200L)
                        .operation(OperationType.PRIMARY)
                        .build())
                .build();

        Person person = Person.builder()
                .firstName("Igor")
                .lastName("Frank")
                .identDocType(InternalIdentityDocumentType.PASSPORT)
                .docNumberSeries("666666 99 11")
                .build();

        when(personRepository.save(person)).thenReturn(person);
        when(personRepository.findPerson("Igor", "Frank", InternalIdentityDocumentType.PASSPORT,
                "666666 99 11")).thenReturn(Optional.of(person));
        when(externalDataConverter.convertExternalToInternalData(request))
                .thenReturn(new Pair<>(InternalIdentityDocumentType.PASSPORT, "666666 99 11"));

        Person result = arrestManagementService.saveOrFindPerson(request);
        assertEquals(person, result);

    }

    @Test
    public void testConvertPersonToEntity() {
        ArrestRequestDto request = new ArrestRequestDto();
        String internalNumSeries = "111111 22 33";
        InternalIdentityDocumentType internalDocType = InternalIdentityDocumentType.PASSPORT;
        request.setFirstName("Maks");
        request.setLastName("Medvedchuk");
        Person actualPerson = arrestManagementService.convertPersonToEntity(request, internalNumSeries, internalDocType);
        Person expectedPerson = Person.builder()
                .firstName("Maks")
                .lastName("Medvedchuk")
                .identDocType(internalDocType)
                .docNumberSeries(internalNumSeries)
                .build();
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void testConvertArrestToEntity() {
        ArrestDto arrestDto = new ArrestDto();
        ArrestRequestDto request = new ArrestRequestDto();
        String internalNumSeries = "111111 22 33";
        InternalIdentityDocumentType internalDocType = InternalIdentityDocumentType.PASSPORT;
        Person person = Person.builder()
                .firstName("Igor")
                .lastName("Frank")
                .identDocType(internalDocType)
                .docNumberSeries(internalNumSeries)
                .build();
        arrestDto.setDocDate(LocalDate.of(2021, 10, 5));
        arrestDto.setDocNum("112fgII");
        arrestDto.setPurpose("Arrest_1");
        arrestDto.setAmount(123456L);
        request.setArrestDto(arrestDto);
        request.setOrganCode(OrganizationCode.SERVICE_OF_BAILIFFS);
        Arrest actualArrest = arrestManagementService.convertArrestToEntity(request, person);
        Arrest expectedArrest = Arrest.builder()
                .organizationCode(OrganizationCode.SERVICE_OF_BAILIFFS)
                .docDate(LocalDate.of(2021, 10, 5))
                .docNum("112fgII")
                .purpose("Arrest_1")
                .amount(123456L)
                .person(person)
                .statusType(StatusType.ACTIVE)
                .build();
        assertEquals(expectedArrest, actualArrest);
    }
}
