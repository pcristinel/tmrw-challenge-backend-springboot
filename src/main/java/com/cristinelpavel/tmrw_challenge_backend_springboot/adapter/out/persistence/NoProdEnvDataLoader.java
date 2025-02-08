package com.cristinelpavel.tmrw_challenge_backend_springboot.adapter.out.persistence;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.port.out.DocumentPersistenceOutPort;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class NoProdEnvDataLoader {

  @Bean
  public CommandLineRunner documentsDataLoader(DocumentPersistenceOutPort documentPersistenceOutPort) {
    Document document1 = Document.builder().id(UUID.fromString("0194e61a-dc22-79f8-8849-32abc2fe1be6")).title("Document 1").build();
    Document document2 = Document.builder().id(UUID.fromString("0194e61a-dc22-7172-af80-6506756f49b3")).title("Document 2").build();
    Document document3 = Document.builder().id(UUID.fromString("0194e61a-dc22-7b12-afee-3e0c1b3c9b4d")).title("Document 3").build();

    List<Document> documentsToBeSaved = List.of(document1, document2, document3);

    return args -> documentPersistenceOutPort.saveAll(documentsToBeSaved);
  }
}
