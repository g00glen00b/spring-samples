package be.g00glen00b.apps.job;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "batch")
@Data
public class MarkdownSolrBatchConfigurationProperties {
    private String pathPattern;
    private String extractPath;
    private String cron;
}
