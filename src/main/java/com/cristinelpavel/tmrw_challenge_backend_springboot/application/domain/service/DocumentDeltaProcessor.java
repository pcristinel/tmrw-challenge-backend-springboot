package com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.service;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.DocumentDelta;
import java.util.List;

public interface DocumentDeltaProcessor {

  String getUpdatedContent(Document currentContent, List<DocumentDelta> deltas);
}
