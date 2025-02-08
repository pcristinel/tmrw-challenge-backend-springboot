package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.rest.dto;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DocumentDto {

  UUID id;
  String title;
  String content;

  /**
   * Converts a Document to a DocumentDto.
   * @param document The Document to convert.
   * @return The converted DocumentDto.
   */
  public static DocumentDto from(Document document) {
    return DocumentDto.builder()
        .id(document.getId())
        .title(document.getTitle())
        .content(document.getContent())
        .build();
  }
}
