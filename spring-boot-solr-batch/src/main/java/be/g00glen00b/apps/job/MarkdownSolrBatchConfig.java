package be.g00glen00b.apps.job;

import be.g00glen00b.apps.processing.HtmlResource;
import be.g00glen00b.apps.processing.MarkdownDocumentOptimizeTasklet;
import be.g00glen00b.apps.processing.MarkdownDocumentWriter;
import be.g00glen00b.apps.processing.MarkdownDocumentProcessor;
import be.g00glen00b.apps.processing.MarkdownDocumentReader;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableBatchProcessing
@EnableScheduling
@EnableConfigurationProperties(MarkdownSolrBatchConfigurationProperties.class)
public class MarkdownSolrBatchConfig {

    @Bean
    public Job indexMarkdownDocumentsJob(JobBuilderFactory jobBuilderFactory, Step indexingStep, Step optimizeStep) {
        return jobBuilderFactory.get("indexMarkdownDocuments")
            .incrementer(new RunIdIncrementer())
            .flow(indexingStep)
            .next(optimizeStep)
            .end()
            .build();
    }

    @Bean
    public Step indexingStep(StepBuilderFactory stepBuilderFactory, MarkdownDocumentReader reader, MarkdownDocumentProcessor processor, MarkdownDocumentWriter writer) {
        return stepBuilderFactory.get("indexingStep")
            .<Resource, HtmlResource> chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    public Step optimizeStep(StepBuilderFactory stepBuilderFactory, MarkdownDocumentOptimizeTasklet tasklet) {
        return stepBuilderFactory.get("optimizeStep")
            .tasklet(tasklet)
            .build();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient solrClient) {
        return new SolrTemplate(solrClient);
    }
}
