package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence;

import com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence.entity.DocumentDeltaEntity;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.DocumentDelta;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.out.DocumentDeltaPersistenceOutPort;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DocumentDeltaPersistenceAdapter implements DocumentDeltaPersistenceOutPort {

  private final DocumentDeltaJpaRepository documentDeltaRepository;

  @Override
  public void saveDocumentDelta(DocumentDelta documentDelta) {
    DocumentDeltaEntity documentDeltaToSave = DocumentDeltaEntity.from(documentDelta);
    documentDeltaRepository.save(documentDeltaToSave).toModel();
  }

  @Override
  public Optional<DocumentDelta> getLatestByDocId(UUID documentId) {
    return documentDeltaRepository.findFirstByDocumentIdOrderByCreatedAtDesc(documentId)
        .map(DocumentDeltaEntity::toModel);
  }

  @Override
  public List<DocumentDelta> findNewDeltaForDocument(UUID documentId, LocalDateTime start) {
    return documentDeltaRepository.findAllByDocumentIdAndCreatedAtAfterOrderByCreatedAtAsc(documentId, start)
        .stream()
        .map(DocumentDeltaEntity::toModel)
        .toList();
  }

}
