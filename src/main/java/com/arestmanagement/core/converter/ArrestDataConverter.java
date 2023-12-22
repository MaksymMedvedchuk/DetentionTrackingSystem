package com.arestmanagement.core.converter;

import com.arestmanagement.core.domain.dto.ArrestDto;
import com.arestmanagement.core.domain.entity.Arrest;
import jakarta.persistence.AttributeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ArrestDataConverter implements AttributeConverter<Arrest, ArrestDto> {

	private final ModelMapper modelMapper;

	public ArrestDataConverter(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public ArrestDto convertToDatabaseColumn(final Arrest arrest) {
		return modelMapper.map(arrest, ArrestDto.class);
	}

	@Override
	public Arrest convertToEntityAttribute(final ArrestDto arrestDto) {
		return modelMapper.map(arrestDto, Arrest.class);
	}
}
