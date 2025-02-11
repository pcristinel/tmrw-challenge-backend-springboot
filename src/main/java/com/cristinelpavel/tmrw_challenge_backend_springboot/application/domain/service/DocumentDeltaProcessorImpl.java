package com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.service;

import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.Document;
import com.cristinelpavel.tmrw_challenge_backend_springboot.application.domain.model.DocumentDelta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentDeltaProcessorImpl implements DocumentDeltaProcessor {

  private final ObjectMapper objectMapper;

  @Override
  public String getUpdatedContent(Document document, List<DocumentDelta> deltas) {
    log.info("Applying delta to document: {}", document.getId());

    try {
      StringBuilder updatedContent = new StringBuilder(document.getContent());

      for (DocumentDelta delta : deltas) {
        JsonNode actions = objectMapper.readTree(delta.getDelta()).elements().next();

        JsonNode op = actions.get(0);
        String firstKey = op.fieldNames().next(); // Get the first key

        // If the operation is "insert" it means that the document content is empty, and it is the first operation in the document
        if (firstKey.equalsIgnoreCase("insert")) {
          String insertText = op.get("insert").asText();
          updatedContent.insert(0, insertText);
          continue;
        }

        // If the operation is "delete" but does not have a "retain" operation, it means that we need to delete everything from the beginning
        // of the document until the offset specified in the "delete" operation
        if (firstKey.equalsIgnoreCase("delete") && !actions.has(1)) {
          int deleteCount = op.get("delete").asInt();
          updatedContent.delete(0, deleteCount);
          continue;
        }

        // If the operation is "retain" it means that the document has content
        if (firstKey.equalsIgnoreCase("retain")) {
          int positionToEdit = op.get("retain").asInt();
          JsonNode action = actions.get(1);
          if (action.has("insert")) {
            String insertText = action.get("insert").asText();
            updatedContent.insert(positionToEdit, insertText);
          } else if (action.has("delete")) {
            int deleteCount = action.get("delete").asInt();
            updatedContent.delete(positionToEdit, positionToEdit + deleteCount);
          }
        }
      }

      return updatedContent.toString();
    } catch (Exception e) {
      log.error("Failed to apply delta", e);
      throw new RuntimeException("Failed to apply delta", e);
    }
  }
}
