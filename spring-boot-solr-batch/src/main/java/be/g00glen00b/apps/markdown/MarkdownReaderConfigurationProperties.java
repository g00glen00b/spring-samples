package be.g00glen00b.apps.markdown;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "reader")
@Data
public class MarkdownReaderConfigurationProperties {
    private String pathPattern;
    private String extractPath;
}
