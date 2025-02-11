package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.DocumentDelta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "document_deltas")
public class DocumentDeltaEntity {

  @Id
  private UUID id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "document_id", nullable = false)
  private DocumentEntity document;

  @Lob
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "JSONB")
  private String delta;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  /**
   * Converts a domain model object to an entity.
   *
   * @param documentDelta the domain model object
   * @return the entity
   */
  public static DocumentDeltaEntity from(DocumentDelta documentDelta) {
    return DocumentDeltaEntity.builder()
        .id(documentDelta.getId())
        .document(DocumentEntity.builder().id(documentDelta.getDocumentId()).build())
        .delta(documentDelta.getDelta())
        .createdAt(documentDelta.getCreatedAt())
        .build();
  }

  /**
   * Converts the entity to a domain model object.
   *
   * @return the domain model object
   */
  public DocumentDelta toModel() {
    return DocumentDelta.builder()
        .id(id)
        .documentId(document.getId())
        .delta(delta)
        .createdAt(createdAt)
        .build();
  }
}
