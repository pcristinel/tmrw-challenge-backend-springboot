package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence;

import com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence.entity.DocumentEntity;
import com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence.entity.DocumentEntityWithoutContentProjection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentJpaRepository extends JpaRepository<DocumentEntity, UUID> {

  @Query("SELECT d.id as id, d.title as title, d.lastUpdated as lastUpdated FROM DocumentEntity d")
  List<DocumentEntityWithoutContentProjection> findAllWithoutContent();

  @Query(value = """
      select distinct doc.id, doc.title, doc.content, doc.last_updated
      from document as doc
      inner join document_deltas as delta
        on doc.id = delta.document_id
      where doc.last_updated < delta.created_at""", nativeQuery = true)
  List<DocumentEntity> findAllDocumentsWithNewDeltas();
}
