package be.g00glen00b.apps.processing;

import be.g00glen00b.apps.document.MarkdownDocument;
import be.g00glen00b.apps.job.MarkdownSolrBatchConfigurationProperties;
import be.g00glen00b.apps.SolrItemWriterException;
import lombok.AllArgsConstructor;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.util.ContentStreamBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class MarkdownDocumentWriter implements ItemWriter<HtmlResource> {
    private static final String FILE_ID_LITERAL = "literal.file.id";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SolrClient solrClient;
    private MarkdownSolrBatchConfigurationProperties configurationProperties;

    @Override
    public void write(List<? extends HtmlResource> list) {
        list.stream().map(this::updateRequest).forEach(this::request);
    }

    private ContentStreamUpdateRequest updateRequest(HtmlResource htmlFile) {
        try {
            ContentStreamUpdateRequest updateRequest = new ContentStreamUpdateRequest(configurationProperties.getExtractPath());
            updateRequest.addContentStream(new ContentStreamBase.StringStream(htmlFile.getHtml(), "text/html;charset=UTF-8"));
            updateRequest.setParam(FILE_ID_LITERAL, htmlFile.getResource().getFile().getAbsolutePath());
            updateRequest.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
            return updateRequest;
        } catch (IOException ex) {
            throw new SolrItemWriterException("Could not retrieve filename", ex);
        }
    }

    private void request(ContentStreamUpdateRequest updateRequest) {
        try {
            solrClient.request(updateRequest, MarkdownDocument.MARKDOWN_CORE);
            logger.info("Updated document in Solr: {}", updateRequest.getParams().get(FILE_ID_LITERAL));
        } catch (SolrServerException | IOException ex) {
            throw new SolrItemWriterException("Could not index document", ex);
        }
    }
}
