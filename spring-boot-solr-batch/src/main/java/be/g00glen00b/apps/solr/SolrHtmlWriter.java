package be.g00glen00b.apps.solr;

import be.g00glen00b.apps.markdown.HtmlRendering;
import be.g00glen00b.apps.markdown.MarkdownReaderConfigurationProperties;
import lombok.AllArgsConstructor;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.util.ContentStreamBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class SolrHtmlWriter implements ItemWriter<HtmlRendering> {
    private static final String FILE_ID_LITERAL = "literal.file.id";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SolrClient solrClient;
    private MarkdownReaderConfigurationProperties configurationProperties;

    @Override
    public void write(List<? extends HtmlRendering> list) throws Exception {
        list.stream().map(this::updateRequest).forEach(this::request);
        solrClient.optimize();
    }

    private ContentStreamUpdateRequest updateRequest(HtmlRendering htmlFile) {
        try {
            ContentStreamUpdateRequest updateRequest = new ContentStreamUpdateRequest(configurationProperties.getExtractPath());
            updateRequest.addContentStream(new ContentStreamBase.StringStream(htmlFile.getFullHtml(), "text/html"));
            updateRequest.setParam(FILE_ID_LITERAL, htmlFile.getResource().getFile().getAbsolutePath());
            updateRequest.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
            return updateRequest;
        } catch (IOException ex) {
            throw new SolrItemWriterException("Could not retrieve filename", ex);
        }
    }

    private void request(ContentStreamUpdateRequest updateRequest) {
        try {
            solrClient.request(updateRequest);
            logger.info("Updated document in Solr: {}", updateRequest.getParams().get(FILE_ID_LITERAL));
        } catch (SolrServerException | IOException ex) {
            throw new SolrItemWriterException("Could not index document", ex);
        }
    }
}
