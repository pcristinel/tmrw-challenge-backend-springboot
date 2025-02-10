package com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.query;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.in.DocumentQueryService;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.out.DocumentPersistenceOutPort;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class DocumentQueryServiceImpl implements DocumentQueryService {

  private final DocumentPersistenceOutPort documentPersistenceOutPort;

  @Override
  public Optional<Document> findById(UUID id) {
    Objects.requireNonNull(id, "id must not be null");

    log.info("DocumentQueryServiceImpl.findById called with id: {}", id);

    return documentPersistenceOutPort.findById(id);
  }

  @Override
  public List<Document> findAllWithoutContent() {
    return documentPersistenceOutPort.findAllWithoutContent();
  }
}
