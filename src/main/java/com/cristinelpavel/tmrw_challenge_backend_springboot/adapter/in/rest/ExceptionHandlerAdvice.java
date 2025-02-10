package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.rest;

import com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.rest.dto.ErrorDto;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.exception.DocumentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler(DocumentNotFoundException.class)
  public ResponseEntity<ErrorDto> handleDocumentNotFoundException(DocumentNotFoundException exception) {
    ErrorDto error = ErrorDto.builder()
        .code("DOCUMENT_NOT_FOUND")
        .title("Document not found")
        .detail(exception.getMessage())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleException(Exception exception) {
    ErrorDto error = ErrorDto.builder()
        .code("INTERNAL_ERROR")
        .title("Internal error")
        .detail(exception.getMessage())
        .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(error);
  }
}
