package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.dto.EmailDto;
import com.detentionsystem.core.domain.entity.Email;
import jakarta.persistence.AttributeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ConvertEmailData implements AttributeConverter<EmailDto, Email> {

	private final ModelMapper modelMapper;

	public ConvertEmailData(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public Email convertToDatabaseColumn(final EmailDto emailDto) {
		return modelMapper.map(emailDto, Email.class);
	}

	@Override
	public EmailDto convertToEntityAttribute(final Email email) {
		return modelMapper.map(email, EmailDto.class);
	}
}

