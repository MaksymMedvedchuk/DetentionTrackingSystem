package com.arestmanagement.core.repository;

import com.arestmanagement.core.domain.entity.Token;
import com.arestmanagement.core.domain.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

	void deleteAllByUserId(Long userId);

	Optional<Token> findByTokenValueAndTokenType(String tokenValue, TokenType tokenType);

	@Query("SELECT userId FROM Token WHERE tokenType = :tokenType AND tokenValue = :tokenValue")
	Optional<Long> findByUserId(@Param("tokenType") TokenType tokenType, @Param("tokenValue") String tokenValue);

	@Query("SELECT tokenValue FROM Token WHERE userId = :userId AND tokenType = :tokenType")
	Optional <String> findTokenValue(@Param("userId") Long userId, @Param("tokenType") TokenType tokenType);
}


