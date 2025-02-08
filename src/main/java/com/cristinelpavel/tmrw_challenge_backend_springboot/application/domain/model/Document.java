package com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Document {

  private UUID id;
  private String title;
  private String content;
}
