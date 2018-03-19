package be.g00glen00b.apps.markdown;

import be.g00glen00b.apps.resource.ResourcePassthroughReader;
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
public class MarkdownFileReader extends MultiResourceItemReader<Resource> {
    private MarkdownReaderConfigurationProperties configurationProperties;

    @PostConstruct
    public void initialize() throws IOException {
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = patternResolver.getResources(configurationProperties.getPathPattern());
        setResources(resources);
        setDelegate(new ResourcePassthroughReader());
    }
}
