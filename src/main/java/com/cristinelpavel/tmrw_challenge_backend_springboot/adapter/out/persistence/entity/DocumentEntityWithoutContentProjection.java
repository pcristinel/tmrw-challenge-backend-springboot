package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DocumentEntityWithoutContentProjection {

  UUID getId();

  String getTitle();

  LocalDateTime getLastUpdated();
}
