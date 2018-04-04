package be.g00glen00b.apps.document;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;

public interface CustomMarkdownDocumentRepository {
    HighlightPage<MarkdownDocument> findDocuments(String searchTerm, Pageable page);
}
