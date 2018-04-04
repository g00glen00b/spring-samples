package be.g00glen00b.apps.document;

import org.springframework.data.solr.repository.SolrCrudRepository;

public interface MarkdownDocumentRepository extends SolrCrudRepository<MarkdownDocument, String>, CustomMarkdownDocumentRepository {
}
