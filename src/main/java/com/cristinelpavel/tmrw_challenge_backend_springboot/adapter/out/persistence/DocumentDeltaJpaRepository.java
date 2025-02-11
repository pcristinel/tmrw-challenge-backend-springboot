package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence;

import com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence.entity.DocumentDeltaEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDeltaJpaRepository extends JpaRepository<DocumentDeltaEntity, UUID> {

  Optional<DocumentDeltaEntity> findFirstByDocumentIdOrderByCreatedAtDesc(UUID documentId);

  List<DocumentDeltaEntity> findAllByDocumentIdAndCreatedAtAfterOrderByCreatedAtAsc(UUID documentId, LocalDateTime start);
}
