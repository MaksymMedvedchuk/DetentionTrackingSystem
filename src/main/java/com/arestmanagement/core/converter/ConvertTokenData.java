package com.arestmanagement.core.converter;

import com.arestmanagement.core.domain.dto.EmailDto;
import com.arestmanagement.core.domain.dto.TokenDto;
import com.arestmanagement.core.domain.entity.Email;
import com.arestmanagement.core.domain.entity.Token;
import jakarta.persistence.AttributeConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ConvertTokenData implements AttributeConverter<TokenDto, Token> {

	private final ModelMapper modelMapper;

	public ConvertTokenData(final ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public Token convertToDatabaseColumn(final TokenDto tokenDto) {
		return modelMapper.map(tokenDto, Token.class);
	}

	@Override
	public TokenDto convertToEntityAttribute(final Token token) {
		return modelMapper.map(token, TokenDto.class);
	}
}

