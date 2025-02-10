package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.rest;

import com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.rest.dto.DocumentDto;
import com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.rest.dto.GetAllDocumentsResponseDto;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.exception.DocumentNotFoundException;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.in.DocumentQueryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/documents")
@CrossOrigin(origins = "*")
public class DocumentController {

  private final DocumentQueryService documentQueryService;

  @GetMapping("/{documentId}")
  public ResponseEntity<DocumentDto> getDocument(@PathVariable("documentId") UUID documentId) {
    return documentQueryService.findById(documentId)
        .map(DocumentDto::from)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new DocumentNotFoundException("Document not found"));
  }

  @GetMapping
  public ResponseEntity<List<GetAllDocumentsResponseDto>> getAllDocuments() {
    List<GetAllDocumentsResponseDto> documents = documentQueryService.findAllWithoutContent()
        .stream()
        .map(GetAllDocumentsResponseDto::from)
        .toList();

    return ResponseEntity.ok(documents);
  }
}
