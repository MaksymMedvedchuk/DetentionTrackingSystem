package com.detentionsystem.core.service;

import com.detentionsystem.core.converter.ExternalDataConverterImpl;
import com.detentionsystem.core.domain.dto.DetentionDto;
import com.detentionsystem.core.domain.dto.DetentionRequestDto;
import com.detentionsystem.core.domain.dto.IdentityDocumentDto;
import com.detentionsystem.core.domain.dto.ResponseDto;
import com.detentionsystem.core.domain.entity.Detention;
import com.detentionsystem.core.domain.entity.Person;
import com.detentionsystem.core.domain.enums.ExternalIdentityDocumentType;
import com.detentionsystem.core.domain.enums.InternalIdentityDocumentType;
import com.detentionsystem.core.domain.enums.OperationType;
import com.detentionsystem.core.domain.enums.OrganizationCode;
import com.detentionsystem.core.domain.enums.ResultCode;
import com.detentionsystem.core.domain.enums.StatusType;
import com.detentionsystem.core.exception.DetentionNotFoundException;
import com.detentionsystem.core.repository.DetentionRepository;
import com.detentionsystem.core.repository.PersonRepository;
import com.detentionsystem.util.Pair;
import com.detentionsystem.validator.OrganCodeMatchValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DetentionTrackingServiceTest {

	@InjectMocks
	private DetentionTrackingServiceImpl detentionTrackingService;
	@Mock
	private ExternalDataConverterImpl mockExternalDataConverter;
	@Mock
	private PersonRepository mockPersonRepository;
	@Mock
	private DetentionRepository mockDetentionRepository;
	@Mock
	private OrganCodeMatchValidator mockOrganCodeMatchValidator;

	private DetentionRequestDto requestDto;

	private DetentionDto detentionDto;

	@BeforeEach
	void setUp(){
		requestDto = mock(DetentionRequestDto.class);
		detentionDto = mock(DetentionDto.class);
	}

	public static DetentionRequestDto createRequest(Long amount, OperationType operationType) {
		return DetentionRequestDto.builder()
			.id(1L)
			.firstName("Maks")
			.lastName("Medvedchuk")
			.organCode(OrganizationCode.SERVICE_OF_BAILIFFS)
			.identityDocumentDto(IdentityDocumentDto.builder()
				.type(ExternalIdentityDocumentType.SERVICE_OF_BAILIFFS_PASSPORT)
				.numberSeries("666666-9911")
				.issueDate(LocalDate.of(2022, 10, 1))
				.build())
			.detentionDto(DetentionDto.builder()
				.docDate(LocalDate.of(2022, 10, 12))
				.docNum("yu544-1567456")
				.purpose("purpose")
				.amount(amount)
				.refDocNum("yu544-1567456_1")
				.operation(operationType)
				.build())
			.build();
	}

	@Test
	@Before
	public void testProcessPrimary() {
		DetentionRequestDto request = createRequest(1200L, OperationType.PRIMARY);
		Person expectedPerson = createPerson();
		Long expectedAmount = 1200L;
		Detention expectedDetention = createDetention(expectedPerson, expectedAmount);
		String expectedInternalDocNum = "666666 99 11";
		when(mockExternalDataConverter.convertExternalToInternalData(request))
			.thenReturn(new Pair<>(InternalIdentityDocumentType.PASSPORT, expectedInternalDocNum));
		when(mockPersonRepository.findPerson(
			request.getFirstName(),
			request.getLastName(),
			InternalIdentityDocumentType.PASSPORT,
			expectedInternalDocNum
		)).thenReturn(Optional.of(expectedPerson));
		when(mockPersonRepository.save(expectedPerson)).thenReturn(expectedPerson);
		when(mockDetentionRepository.save(expectedDetention)).thenReturn(expectedDetention);
		ResponseDto expectedResponse = createResponse(expectedDetention);
		ResponseDto actualResponse = detentionTrackingService.processRequest(request);
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void processChangedToActiveStatus() {
		DetentionRequestDto request = createRequest(1500L, OperationType.CHANGED);
		Detention detention = createDetention(2000L);
		when(mockDetentionRepository.save(detention)).thenReturn(detention);
		when(mockDetentionRepository.findByDocNum(request.getDetentionDto().getRefDocNum())).thenReturn(Optional.of(
			detention));
		ResponseDto actualResponse = detentionTrackingService.processRequest(request);
		ResponseDto expectedResponse = createResponse(detention);
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void processChangedToActiveStatusIfArrestEmpty() {
		DetentionRequestDto request = createRequest(1500L, OperationType.CHANGED);
		Detention detention = createDetention(2000L);
		when(mockDetentionRepository.save(detention)).thenReturn(detention);
		when(mockDetentionRepository.findByDocNum(request
			.getDetentionDto()
			.getRefDocNum())).thenReturn(Optional.empty());
		assertThrows(DetentionNotFoundException.class, () -> detentionTrackingService.processRequest(request));
	}

	@Test
	public void processChangedToCanceledStatus() {
		DetentionRequestDto request = createRequest(1500L, OperationType.CHANGED);
		Detention detention = createDetention(200L);
		when(mockDetentionRepository.findByDocNum(request.getDetentionDto().getRefDocNum())).thenReturn(Optional.of(
			detention));
		when(mockDetentionRepository.save(detention)).thenReturn(detention);
		ResponseDto actualResponse = detentionTrackingService.processRequest(request);
		ResponseDto expectedResponse = createResponse(detention);
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testProcessCanceled() {
		DetentionRequestDto request = createRequest(1500L, OperationType.CANCELED);
		Detention detention = createDetention(1200L);
		when(mockDetentionRepository.findByDocNum(request.getDetentionDto().getRefDocNum())).thenReturn(Optional.of(
			detention));
		when(mockDetentionRepository.save(detention)).thenReturn(detention);
		ResponseDto expectedResponse = createResponse(detention);
		ResponseDto actualResponse = detentionTrackingService.processRequest(request);
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testConvertPersonToEntity() {
		DetentionRequestDto request = new DetentionRequestDto();
		request.setFirstName("Maks");
		request.setLastName("Medvedchuk");
		String internalNumSeries = "666666 99 11";
		InternalIdentityDocumentType internalDocType = InternalIdentityDocumentType.PASSPORT;
		Person expectedPerson = createPerson();
		Person
			actualPerson =
			detentionTrackingService.convertPersonToEntity(request, internalNumSeries, internalDocType);
		assertEquals(expectedPerson, actualPerson);
	}

	@Test
	public void testConvertPersonToEntityDataMismatch() {
		DetentionRequestDto request = new DetentionRequestDto();
		request.setFirstName("Mak");
		request.setLastName("Medvedchuk");
		String internalNumSeries = "666666 99 11";
		InternalIdentityDocumentType internalDocType = InternalIdentityDocumentType.PASSPORT;
		Person expectedPerson = createPerson();
		Person
			actualPerson =
			detentionTrackingService.convertPersonToEntity(request, internalNumSeries, internalDocType);
		assertNotEquals(expectedPerson, actualPerson);
	}

	private ResponseDto createResponse(Detention actualDetention) {
		ResponseDto expectedResponse = new ResponseDto();
		expectedResponse.setArrestId(actualDetention.getId());
		expectedResponse.setResultCode(ResultCode.SUCCESS);
		return expectedResponse;
	}

	private Detention createDetention(Person person, Long amount) {
		return Detention.builder()
			.organizationCode(OrganizationCode.SERVICE_OF_BAILIFFS)
			.docDate(LocalDate.of(2022, 10, 12))
			.docNum("yu544-1567456")
			.purpose("purpose")
			.amount(amount)
			.person(person)
			.statusType(StatusType.ACTIVE)
			.build();
	}

	private Detention createDetention(Long amount) {
		return createDetention(null, amount);
	}

	private Person createPerson() {
		return Person.builder()
			.firstName("Maks")
			.lastName("Medvedchuk")
			.identDocType(InternalIdentityDocumentType.PASSPORT)
			.docNumberSeries("666666 99 11")
			.build();
	}

	@Test
	public void shouldProcessRequest() {

		/*when(requestDto.getDetentionDto()).thenReturn(detentionDto);*/

		ResponseDto responseDto = detentionTrackingService.processRequest(requestDto);

		verify(mockOrganCodeMatchValidator).validateOrganCodeMatch(requestDto);
	}
}
