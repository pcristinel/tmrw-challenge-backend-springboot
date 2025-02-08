package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.in.websocket;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.in.CreateDocumentInPort;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.in.DocumentQueryService;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@RequiredArgsConstructor
@Component
public class DocumentEditWebSocketHandler extends TextWebSocketHandler {

  private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
  private final DocumentQueryService documentQueryService;
  private final CreateDocumentInPort createDocumentInPort;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    sessions.add(session);


  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    for (WebSocketSession s : sessions) {
      if (s.isOpen() && !s.getId().equals(session.getId())) {
        s.sendMessage(message);
      }
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    sessions.remove(session);
  }
}
