package com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.service.query;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.DocumentDelta;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.service.DocumentDeltaProcessor;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.in.DocumentQueryService;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.out.DocumentDeltaPersistenceOutPort;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.out.DocumentPersistenceOutPort;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class DocumentQueryServiceImpl implements DocumentQueryService {

  private final DocumentPersistenceOutPort documentPersistenceOutPort;
  private final DocumentDeltaPersistenceOutPort documentDeltaPersistenceOutPort;
  private final DocumentDeltaProcessor documentDeltaProcessor;

  @Override
  @Transactional
  public Optional<Document> findById(UUID id) {
    Objects.requireNonNull(id, "id must not be null");

    log.info("DocumentQueryServiceImpl.findById called with id: {}", id);

    Optional<Document> document = documentPersistenceOutPort.findById(id);

    if (document.isEmpty()) {
      return Optional.empty();
    }

    Optional<DocumentDelta> documentDelta = documentDeltaPersistenceOutPort.getLatestByDocId(id);

    if (documentDelta.isPresent() && documentHasNewDelta(document.get(), documentDelta.get())) {
      log.info("There is new delta that current document does not have. Applying delta to document...");
      List<DocumentDelta> newDeltaForDocument = documentDeltaPersistenceOutPort.findNewDeltaForDocument(id,
          document.get().getLastUpdated());
      String updatedContent = documentDeltaProcessor.getUpdatedContent(document.get(), newDeltaForDocument);
      document.get().setContent(updatedContent);
    }

    return document;
  }

  @Override
  public List<Document> findAllWithoutContent() {
    return documentPersistenceOutPort.findAllWithoutContent();
  }

  private boolean documentHasNewDelta(Document document, DocumentDelta documentDelta) {
    return documentDelta.getCreatedAt().isAfter(document.getLastUpdated());
  }
}
