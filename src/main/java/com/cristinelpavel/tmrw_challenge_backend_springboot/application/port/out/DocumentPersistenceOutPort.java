package com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.out;


import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentPersistenceOutPort {

  /**
   * Save a document
   *
   * @param document the document to be saved
   */
  void saveDocument(Document document);

  /**
   * Saves a list of documents
   *
   * @param documents the documents to be saved
   */
  void saveAll(List<Document> documents);

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
  List<Document> findAllWithoutContent();

  /**
   * Finds all documents that need to be updated because they have new deltas
   */
  List<Document> findAllDocumentsWithNewDeltas();
}
