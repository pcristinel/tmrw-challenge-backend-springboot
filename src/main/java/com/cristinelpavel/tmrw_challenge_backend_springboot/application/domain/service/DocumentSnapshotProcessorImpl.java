package com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.service;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.DocumentDelta;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.out.DocumentDeltaPersistenceOutPort;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.out.DocumentPersistenceOutPort;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DocumentSnapshotProcessorImpl implements DocumentSnapshotProcessor {

  private final DocumentPersistenceOutPort documentPersistenceOutPort;
  private final DocumentDeltaPersistenceOutPort documentDeltaPersistenceOutPort;
  private final DocumentDeltaProcessor documentDeltaProcessor;

  // Virtual thread pool for concurrency
  private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

  @Transactional
  @Scheduled(fixedRate = 10000) // 10 seconds
  @Override
  public void processSnapshotForUpdatedDocuments() {
    List<Document> documentsWithNewDeltas = documentPersistenceOutPort.findAllDocumentsWithNewDeltas();

    if (documentsWithNewDeltas.isEmpty()) {
      log.info("No documents with new deltas found, nothing to process, exiting...");
      return;
    }

    log.info("Processing snapshot for {} documents", documentsWithNewDeltas.size());

    documentsWithNewDeltas.forEach(document -> {
      executorService.submit(() -> {
        log.info("Processing snapshot for document with id: {}", document.getId());
        List<DocumentDelta> deltas = documentDeltaPersistenceOutPort.findNewDeltaForDocument(document.getId(),
            document.getLastUpdated());
        String updatedContent = documentDeltaProcessor.getUpdatedContent(document, deltas);
        document.setLastUpdated(LocalDateTime.now(ZoneOffset.UTC));
        document.setContent(updatedContent);
        documentPersistenceOutPort.saveDocument(document);
      });
    });
  }
}
