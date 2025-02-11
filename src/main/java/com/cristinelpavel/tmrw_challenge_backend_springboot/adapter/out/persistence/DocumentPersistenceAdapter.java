package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence;

import com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence.entity.DocumentEntity;
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
  public void saveDocument(Document document) {
    DocumentEntity documentToBeSaved = DocumentEntity.from(document);
    documentRepository.save(documentToBeSaved).toModel();
  }

  @Override
  public void saveAll(List<Document> documents) {
    List<DocumentEntity> documentsToBeSaved = documents.stream().map(DocumentEntity::from).toList();
    documentRepository.saveAll(documentsToBeSaved);
  }

  @Override
  public Optional<Document> findById(UUID id) {
    return documentRepository.findById(id).map(DocumentEntity::toModel);
  }

  @Override
  public List<Document> findAllWithoutContent() {
    return documentRepository.findAllWithoutContent()
        .stream()
        .map(document -> Document.builder()
            .id(document.getId())
            .title(document.getTitle())
            .lastUpdated(document.getLastUpdated())
            .build()
        )
        .toList();
  }

  @Override
  public List<Document> findAllDocumentsWithNewDeltas() {
    return documentRepository.findAllDocumentsWithNewDeltas()
        .stream()
        .map(DocumentEntity::toModel)
        .toList();
  }
}
