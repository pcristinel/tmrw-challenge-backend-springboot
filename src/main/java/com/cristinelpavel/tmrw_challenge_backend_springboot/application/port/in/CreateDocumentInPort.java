package com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.in;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.in.command.CreateDocumentCommand;

public interface CreateDocumentInPort {

  Document invoke(CreateDocumentCommand command);
}
