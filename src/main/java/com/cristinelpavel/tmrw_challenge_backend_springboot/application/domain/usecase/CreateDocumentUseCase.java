package com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.usecase;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.in.CreateDocumentInPort;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.in.command.CreateDocumentCommand;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.out.DocumentPersistenceOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateDocumentUseCase implements CreateDocumentInPort {

  private final DocumentPersistenceOutPort documentPersistenceOutPort;

  @Override
  public Document invoke(CreateDocumentCommand command) {
    // TODO: Add CommandValidator
    // commandValidator.validate(command);

    log.info("Creating document invoked with the following data: {}", command);


    return null;
  }
}
