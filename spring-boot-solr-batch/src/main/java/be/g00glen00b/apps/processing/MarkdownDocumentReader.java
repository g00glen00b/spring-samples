package be.g00glen00b.apps.processing;

import be.g00glen00b.apps.job.MarkdownSolrBatchConfigurationProperties;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@AllArgsConstructor
public class MarkdownDocumentReader extends MultiResourceItemReader<Resource> {
    private MarkdownSolrBatchConfigurationProperties configurationProperties;

    @PostConstruct
    public void initialize() throws IOException {
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = patternResolver.getResources(configurationProperties.getPathPattern());
        setResources(resources);
        setDelegate(new ResourcePassthroughReader());
    }
}
