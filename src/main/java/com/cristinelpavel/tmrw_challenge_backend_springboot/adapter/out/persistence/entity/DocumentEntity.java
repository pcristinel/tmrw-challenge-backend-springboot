package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence.entity;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "document")
public class DocumentEntity {

  @Id
  private UUID id;

  @Column(nullable = false, unique = true)
  private String title;

  @Lob
  @Column(columnDefinition = "TEXT")
  private String content;

  @Column(nullable = false)
  private LocalDateTime lastUpdated;

  /**
   * Converts a domain model object to an entity.
   *
   * @param document the domain model object
   * @return the entity
   */
  public static DocumentEntity from(Document document) {
    return DocumentEntity.builder()
        .id(document.getId())
        .title(document.getTitle())
        .content(document.getContent())
        .lastUpdated(document.getLastUpdated())
        .build();
  }

  /**
   * Converts the entity to a domain model object.
   *
   * @return the domain model object
   */
  public Document toModel() {
    return Document.builder()
        .id(id)
        .title(title)
        .content(content)
        .lastUpdated(lastUpdated)
        .build();
  }
}
