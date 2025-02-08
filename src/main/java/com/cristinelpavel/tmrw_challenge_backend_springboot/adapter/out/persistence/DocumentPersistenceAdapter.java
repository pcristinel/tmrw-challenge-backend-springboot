package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.out.DocumentPersistenceOutPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DocumentPersistenceAdapter implements DocumentPersistenceOutPort {

  private final DocumentJpaRepository documentRepository;

  @Override
  public Document saveDocument(Document document) {
    DocumentEntity documentToBeSaved = DocumentEntity.from(document);
    return documentRepository.save(documentToBeSaved).toDocument();
  }

  @Override
  public List<Document> saveAll(List<Document> documents) {
    return documentRepository.saveAll(documents.stream().map(DocumentEntity::from).toList())
        .stream()
        .map(DocumentEntity::toDocument)
        .toList();
  }

  @Override
  public Optional<Document> findById(UUID id) {
    return documentRepository.findById(id).map(DocumentEntity::toDocument);
  }

  @Override
  public List<Document> findAll() {
    return documentRepository.findAll()
        .stream()
        .map(DocumentEntity::toDocument)
        .toList();
  }
}
