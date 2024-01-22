package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.dto.UserDto;
import com.detentionsystem.core.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConvertUserDataTest {

	@InjectMocks
	private ConvertUserData convertUserData;

	@Mock
	private ModelMapper modelMapper;

	private UserDto userDto;

	private User expectedUser;

	private User user;

	private UserDto expectedUserDto;

	@BeforeEach
	void setUp() {
		userDto = new UserDto();
		expectedUser = new User();
		user = new User();
		expectedUserDto = new UserDto();
	}

	@Test
	void shouldConvertToDatabaseColumn() {
		when(modelMapper.map(userDto, User.class)).thenReturn(expectedUser);

		User actualUser = convertUserData.convertToDatabaseColumn(userDto);

		assertEquals(actualUser, expectedUser);
		assertNotNull(actualUser, "Mapping result should not be null");
		verify(modelMapper).map(userDto, User.class);
	}

	@Test
	void shouldConvertToEntityAttribute() {
		when(modelMapper.map(user, UserDto.class)).thenReturn(expectedUserDto);

		UserDto actualUserDto = convertUserData.convertToEntityAttribute(user);

		assertEquals(actualUserDto, expectedUserDto);
		assertNotNull(actualUserDto, "Mapping result should not be null");
		verify(modelMapper).map(user, UserDto.class);
	}
}
