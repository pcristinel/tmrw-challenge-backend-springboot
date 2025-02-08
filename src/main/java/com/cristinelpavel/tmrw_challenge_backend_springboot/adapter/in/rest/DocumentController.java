package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.rest;

import com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.rest.dto.DocumentDto;
import com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.rest.dto.GetAllDocumentsResponseDto;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.in.DocumentQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

  private final DocumentQueryService documentQueryService;

  @GetMapping
  public ResponseEntity<GetAllDocumentsResponseDto> getAllDocuments() {
    List<DocumentDto> documents = documentQueryService.findAll()
        .stream()
        .map(DocumentDto::from)
        .toList();

    return ResponseEntity.ok(new GetAllDocumentsResponseDto(documents));
  }
}
