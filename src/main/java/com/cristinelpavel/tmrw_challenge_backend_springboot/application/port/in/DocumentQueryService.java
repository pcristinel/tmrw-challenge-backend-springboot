package com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.in;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentQueryService {

  /**
   * Find a document by its id
   *
   * @param id the id of the document, must not be null
   * @return the document with the given id or Optional.empty() if none found
   */
  Optional<Document> findById(UUID id);

  /**
   * Find all documents
   *
   * @return a list of all documents
   */
  List<Document> findAll();
}
