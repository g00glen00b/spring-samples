package be.g00glen00b.apps.document;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightPage;

@AllArgsConstructor
public class MarkdownDocumentRepositoryImpl implements CustomMarkdownDocumentRepository {
    private SolrTemplate solrTemplate;

    @Override
    public HighlightPage<MarkdownDocument> findDocuments(String searchTerm, Pageable page) {
        Criteria fileIdCriteria = new Criteria(MarkdownDocument.FILE_ID_FIELD).boost(2).is(searchTerm);
        Criteria contentCriteria = new Criteria(MarkdownDocument.CONTENT_FIELD).fuzzy(searchTerm);
        SimpleHighlightQuery query = new SimpleHighlightQuery(fileIdCriteria.or(contentCriteria), page);
        query.setHighlightOptions(new HighlightOptions()
            .setSimplePrefix("<strong>")
            .setSimplePostfix("</strong>")
            .addField(MarkdownDocument.FILE_ID_FIELD, MarkdownDocument.CONTENT_FIELD));
        return solrTemplate.queryForHighlightPage(MarkdownDocument.MARKDOWN_CORE, query, MarkdownDocument.class);
    }
}
