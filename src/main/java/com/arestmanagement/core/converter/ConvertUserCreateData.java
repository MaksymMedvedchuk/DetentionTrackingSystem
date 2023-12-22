package com.arestmanagement.core.converter;

import com.arestmanagement.core.domain.dto.UserCreationDto;
import com.arestmanagement.core.domain.dto.UserDto;
import com.arestmanagement.core.domain.entity.User;
import jakarta.persistence.AttributeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ConvertUserCreateData implements AttributeConverter<UserDto, User> {

	private final ModelMapper modelMapper;

	public ConvertUserCreateData(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public User convertToDatabaseColumn(final UserDto userDto) {
		return modelMapper.map(userDto, User.class);
	}

	@Override
	public UserDto convertToEntityAttribute(final User user) {
		return modelMapper.map(user, UserDto.class);
	}
}

