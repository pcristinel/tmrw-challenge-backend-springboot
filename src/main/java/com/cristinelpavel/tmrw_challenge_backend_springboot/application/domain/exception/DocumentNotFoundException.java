package com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.exception;

public class DocumentNotFoundException extends RuntimeException {

  public DocumentNotFoundException(String message) {
    super(message);
  }
}
