package com.arestmanagement.exception;

import com.arestmanagement.dto.ResponseDto;
import com.arestmanagement.util.ResultCode;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.media.sound.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CustomerRestExceptionHandler {

    @ExceptionHandler()
    private ResponseEntity<ResponseDto> handleOrganCodeNotMatchEx(ValidationException ex) {
        ResponseDto responseDtoError = new ResponseDto(ResultCode.BUSINESS_DATA_ERROR, ex.getMessage());
        return new ResponseEntity<>(responseDtoError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    private ResponseEntity<ResponseDto> handleMethodArgumentNotValidEx(MethodArgumentNotValidException ex) {
        ResponseDto responseDtoError = new ResponseDto(ResultCode.BUSINESS_DATA_ERROR, ex.getMessage());
        return new ResponseEntity<>(responseDtoError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler()
    private ResponseEntity<ResponseDto> handleArrestNotFoundException(ArrestNotFoundException ex){
        ResponseDto responseDtoError = new ResponseDto(ResultCode.BUSINESS_DATA_ERROR, ex.getMessage());
        return new ResponseEntity<>(responseDtoError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseDto> handleHttpMessageNotReadableEx(HttpMessageNotReadableException ex) {
        StringBuilder message = new StringBuilder(ex.getMessage());
        Throwable cause = ex.getCause();
        JsonMappingException jme;
        if (cause instanceof JsonMappingException) {
            jme = (JsonMappingException) cause;
            List<JsonMappingException.Reference> refs = jme.getPath();
            for (JsonMappingException.Reference ref : refs) {
                if (ref.getFieldName().contains("DocDate") || ref.getFieldName().contains("IssueDate"))
                    message.append(". Expected pattern is dd-mm-yyyy.");
            }
        }
        ResponseDto responseDtoError = new ResponseDto(ResultCode.BUSINESS_DATA_ERROR, message.toString());
        return new ResponseEntity<>(responseDtoError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseDto> handleDataIntegrityViolationEx(DataIntegrityViolationException ex) {
        ResponseDto responseDtoError = new ResponseDto(ResultCode.BUSINESS_DATA_ERROR, ex.getMessage());
        return new ResponseEntity<>(responseDtoError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseDto> handleGeneralException(Exception ex) {
        ResponseDto responseDtoError = new ResponseDto(ResultCode.TECHNICAL_ERROR, ex.getMessage());
        return new ResponseEntity<>(responseDtoError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
