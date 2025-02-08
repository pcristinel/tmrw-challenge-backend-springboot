package com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateDocumentCommand {

  private final String title;
  private final String content;
}
