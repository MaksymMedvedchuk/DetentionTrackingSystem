package com.detentionsystem.core.exception;

import com.detentionsystem.config.Constant;
import com.detentionsystem.core.domain.dto.ResponseDto;
import com.detentionsystem.core.domain.enums.ResultCode;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class CustomerRestExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	private ResponseEntity<ResponseDto> handleOrganCodeNotMatchEx(ValidationException ex) {
		ResponseDto responseDtoError = new ResponseDto(ResultCode.BUSINESS_DATA_ERROR, ex.getMessage());
		return new ResponseEntity<>(responseDtoError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ResponseEntity<ResponseDto> handleMethodArgumentNotValidEx(MethodArgumentNotValidException ex) {
		ResponseDto responseDtoError = new ResponseDto(ResultCode.BUSINESS_DATA_ERROR, ex.getMessage());
		return new ResponseEntity<>(responseDtoError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DetentionNotFoundException.class)
	private ResponseEntity<ResponseDto> handleArrestNotFoundException(DetentionNotFoundException ex) {
		ResponseDto responseDtoError = new ResponseDto(ResultCode.BUSINESS_DATA_ERROR, ex.getMessage());
		return new ResponseEntity<>(responseDtoError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	private ResponseEntity<ResponseDto> handleHttpMessageNotReadableEx(HttpMessageNotReadableException ex) {
		StringBuilder message = new StringBuilder(ex.getMessage());
		Throwable cause = ex.getCause();
		JsonMappingException jme;
		if (cause instanceof JsonMappingException) {
			jme = (JsonMappingException) cause;
			List<JsonMappingException.Reference> refs = jme.getPath();
			for (JsonMappingException.Reference ref : refs) {
				if (ref.getFieldName().contains("DocDate") || ref.getFieldName().contains("IssueDate")) {
					message.append(". Expected pattern is dd-mm-yyyy.");
				}
			}
		}
		ResponseDto responseDtoError = new ResponseDto(ResultCode.BUSINESS_DATA_ERROR, message.toString());
		return new ResponseEntity<>(responseDtoError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	private ResponseEntity<ResponseDto> handleDataIntegrityViolationEx(DataIntegrityViolationException ex) {
		ResponseDto responseDtoError = new ResponseDto(ResultCode.BUSINESS_DATA_ERROR, ex.getMessage());
		return new ResponseEntity<>(responseDtoError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadCredentialsException.class)
	private ResponseEntity<ResponseDto> handleBadCredentialsException(BadCredentialsException ex) {
		ResponseDto responseDtoError = new ResponseDto(ResultCode.TECHNICAL_ERROR, ex.getMessage());
		return new ResponseEntity<>(responseDtoError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	private ResponseEntity<ResponseDto> handleGeneralException(Exception ex) {
		ResponseDto responseDtoError = new ResponseDto(ResultCode.TECHNICAL_ERROR, ex.getMessage());
		return new ResponseEntity<>(responseDtoError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	private ResponseEntity<ResponseDto> handleExpiredJwtException(ExpiredJwtException ex) {
		ResponseDto responseDtoError = new ResponseDto(ResultCode.TECHNICAL_ERROR, ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
			.header(Constant.Token.ACCESS_TOKEN, ex.getMessage())
			.body(responseDtoError);
	}

	@ExceptionHandler(UnsupportedJwtException.class)
	private ResponseEntity<ResponseDto> handleUnsupportedJwtException(UnsupportedJwtException ex) {
		ResponseDto responseDtoError = new ResponseDto(ResultCode.TECHNICAL_ERROR, ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
			.header(Constant.Token.ACCESS_TOKEN, ex.getMessage())
			.body(responseDtoError);
	}

	@ExceptionHandler(MalformedJwtException.class)
	private ResponseEntity<ResponseDto> handleMalformedJwtException(MalformedJwtException ex) {
		ResponseDto responseDtoError = new ResponseDto(ResultCode.TECHNICAL_ERROR, ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
			.header(Constant.Token.ACCESS_TOKEN, ex.getMessage())
			.body(responseDtoError);
	}

	@ExceptionHandler(SignatureException.class)
	private ResponseEntity<ResponseDto> handleSignatureException(SignatureException ex) {
		ResponseDto responseDtoError = new ResponseDto(ResultCode.TECHNICAL_ERROR, ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
			.header(Constant.Token.ACCESS_TOKEN, ex.getMessage())
			.body(responseDtoError);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	private ResponseEntity<ResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
		ResponseDto responseDtoError = new ResponseDto(ResultCode.TECHNICAL_ERROR, ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
			.header(Constant.Token.ACCESS_TOKEN, ex.getMessage())
			.body(responseDtoError);
	}
}