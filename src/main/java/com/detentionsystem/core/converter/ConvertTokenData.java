package com.detentionsystem.core.converter;

import com.detentionsystem.core.domain.dto.TokenDto;
import com.detentionsystem.core.domain.entity.Token;
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

