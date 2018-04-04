package be.g00glen00b.apps.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.repository.Score;

import java.time.LocalDateTime;

@Data
@SolrDocument(solrCoreName = MarkdownDocument.MARKDOWN_CORE)
public class MarkdownDocument {
    public static final String MARKDOWN_CORE = "markdown";
    public static final String FILE_ID_FIELD = "file.id";
    public static final String CONTENT_FIELD = "content";
    public static final String LAST_MODIFIED_FIELD = "last_modified";
    @Id
    @Indexed(FILE_ID_FIELD)
    private String id;
    @Indexed(CONTENT_FIELD)
    private String content;
    @Indexed(LAST_MODIFIED_FIELD)
    private LocalDateTime lastModified;
    @Score
    private float score;
}
