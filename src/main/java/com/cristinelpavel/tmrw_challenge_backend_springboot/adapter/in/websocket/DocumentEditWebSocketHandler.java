package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.websocket;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.DocumentDelta;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.out.DocumentDeltaPersistenceOutPort;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class DocumentEditWebSocketHandler extends TextWebSocketHandler {

  // Map to store document ID -> set of WebSocket sessions
  private final ConcurrentHashMap<UUID, Set<WebSocketSession>> documentSessions = new ConcurrentHashMap<>();
  private final DocumentDeltaPersistenceOutPort documentDeltaPersistenceOutPort;

  // Virtual thread pool for concurrency
  private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    UUID documentId = extractDocumentId(session);
    if (documentId == null) {
      session.close(CloseStatus.BAD_DATA);
      return;
    }

    documentSessions
        .computeIfAbsent(documentId, key -> new CopyOnWriteArraySet<>())
        .add(session);

    log.info("User connected to document: {}", documentId);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) {
    UUID documentId = extractDocumentId(session);
    if (documentId == null) {
      return;
    }

    log.info("Received update for document {}: {}", documentId, message.getPayload());

    // Submit broadcasting to the virtual thread pool
    executorService.submit(() -> broadcastUpdate(documentId, session, message));

    // Submit database persistence to the virtual thread pool
    executorService.submit(() -> saveDeltaToDatabase(documentId, message.getPayload()));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    UUID documentId = extractDocumentId(session);
    if (documentId != null) {
      documentSessions.getOrDefault(documentId, Set.of()).remove(session);
      if (documentSessions.get(documentId).isEmpty()) {
        documentSessions.remove(documentId);
      }
    }
  }

  private UUID extractDocumentId(WebSocketSession session) {
    String path = Objects.requireNonNull(session.getUri()).getPath();
    String[] parts = path.split("/");
    return (parts.length >= 3) ? UUID.fromString(parts[2]) : null; // Assumes URL is /documents/{documentId}/edit
  }

  private void saveDeltaToDatabase(UUID documentId, String jsonDelta) {
    DocumentDelta newDocumentDelta = DocumentDelta.builder()
        .id(UUID.randomUUID())
        .documentId(documentId)
        .delta(jsonDelta)
        .createdAt(LocalDateTime.now(ZoneOffset.UTC))
        .build();

    documentDeltaPersistenceOutPort.saveDocumentDelta(newDocumentDelta);
  }

  private void broadcastUpdate(UUID documentId, WebSocketSession senderSession, TextMessage message) {
    for (WebSocketSession session : documentSessions.getOrDefault(documentId, Set.of())) {
      if (session.isOpen() && !session.equals(senderSession)) {
        try {
          session.sendMessage(message);
        } catch (Exception e) {
          log.error("Failed to broadcast update to session", e);
        }
      }
    }
  }
}
