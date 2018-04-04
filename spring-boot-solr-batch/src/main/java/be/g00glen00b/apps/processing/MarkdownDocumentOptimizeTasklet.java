package be.g00glen00b.apps.processing;

import be.g00glen00b.apps.document.MarkdownDocument;
import lombok.AllArgsConstructor;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MarkdownDocumentOptimizeTasklet implements Tasklet {
    private SolrClient solrClient;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        solrClient.optimize(MarkdownDocument.MARKDOWN_CORE);
        return RepeatStatus.FINISHED;
    }
}
