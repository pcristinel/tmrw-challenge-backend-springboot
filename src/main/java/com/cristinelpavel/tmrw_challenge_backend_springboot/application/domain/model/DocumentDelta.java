package com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDelta {

  private UUID id;
  private UUID documentId;
  private String delta;
  private LocalDateTime createdAt;
}
