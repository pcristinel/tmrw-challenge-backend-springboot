package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.websocket;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
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
  private final ConcurrentHashMap<String, Set<WebSocketSession>> documentSessions = new ConcurrentHashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    String documentId = extractDocumentId(session);
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
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String documentId = extractDocumentId(session);
    if (documentId == null) {
      return;
    }

    log.info("Received update for document {}: {}", documentId, message.getPayload());

    // Broadcast to all sessions editing the same document
    for (WebSocketSession docSession : documentSessions.getOrDefault(documentId, Set.of())) {
      if (docSession.isOpen() && !docSession.equals(session)) {
        docSession.sendMessage(message);
      }
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    String documentId = extractDocumentId(session);
    if (documentId != null) {
      documentSessions.getOrDefault(documentId, Set.of()).remove(session);
      if (documentSessions.get(documentId).isEmpty()) {
        documentSessions.remove(documentId);
      }
    }
  }

  private String extractDocumentId(WebSocketSession session) {
    String path = Objects.requireNonNull(session.getUri()).getPath();
    String[] parts = path.split("/");
    return (parts.length >= 3) ? parts[2] : null; // Assumes URL is /documents/{documentId}/edit
  }
}
