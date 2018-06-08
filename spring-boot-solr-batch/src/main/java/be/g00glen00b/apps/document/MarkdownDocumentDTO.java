package be.g00glen00b.apps.document;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class MarkdownDocumentDTO {
    private String id;
    private LocalDateTime lastModified;
    private String content;
    private float score;
    private Map<String, List<String>> highlights;
}
