package be.g00glen00b.apps.markdown;

import be.g00glen00b.apps.solr.SolrHtmlWriter;
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

@Configuration
@EnableBatchProcessing
@EnableConfigurationProperties(MarkdownReaderConfigurationProperties.class)
public class MarkdownSolrBatchConfig {

    @Bean
    public Job indexMarkdownDocumentsJob(JobBuilderFactory jobBuilderFactory, Step step1) {
        return jobBuilderFactory.get("indexMarkdownDocuments")
            .incrementer(new RunIdIncrementer())
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, MarkdownFileReader reader, MarkdownFileHtmlProcessor processor, SolrHtmlWriter writer) {
        return stepBuilderFactory.get("step1")
            .<Resource, HtmlRendering> chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

}
