package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.dto.DetentionDto;
import com.detentionsystem.core.domain.entity.Detention;
import jakarta.persistence.AttributeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DetentionConverter implements AttributeConverter<Detention, DetentionDto> {

	private final ModelMapper modelMapper;

	public DetentionConverter(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public DetentionDto convertToDatabaseColumn(final Detention detention) {
		return modelMapper.map(detention, DetentionDto.class);
	}

	@Override
	public Detention convertToEntityAttribute(final DetentionDto detentionDto) {
		return modelMapper.map(detentionDto, Detention.class);
	}
}
