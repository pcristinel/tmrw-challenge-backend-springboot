package com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.out;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.DocumentDelta;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentDeltaPersistenceOutPort {

  /**
   * Saves a document delta
   *
   * @param documentDelta the document delta to be saved. Must not be null
   */
  void saveDocumentDelta(DocumentDelta documentDelta);

  /**
   * Searches for the latest document delta for a given document id
   *
   * @param documentId the id of the document. Must not be null
   * @return the latest document delta, if any, for the given document id
   */
  Optional<DocumentDelta> getLatestByDocId(UUID documentId);

  /**
   * Searches for new document deltas since the last time the document was updated
   *
   * @param documentId the id of the document. Must not be null
   * @param start the start of the period. Must not be null
   * @return a list of all document deltas, if any, for the given document id in the given period
   */
  List<DocumentDelta> findNewDeltaForDocument(UUID documentId, LocalDateTime start);
}
