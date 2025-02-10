package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence;

import java.util.UUID;

public interface DocumentEntityWithoutContentProjection {

  UUID getId();

  String getTitle();
}
