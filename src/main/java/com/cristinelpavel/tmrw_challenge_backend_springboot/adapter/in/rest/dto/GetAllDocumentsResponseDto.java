package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.rest.dto;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import java.util.UUID;

public record GetAllDocumentsResponseDto(UUID id, String title) {

  public static GetAllDocumentsResponseDto from(Document document) {
    return new GetAllDocumentsResponseDto(document.getId(), document.getTitle());
  }
}
