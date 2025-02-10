package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentJpaRepository extends JpaRepository<DocumentEntity, UUID> {

  @Query("SELECT d.id as id, d.title as title FROM DocumentEntity d")
  List<DocumentEntityWithoutContentProjection> findAllWithoutContent();
}
