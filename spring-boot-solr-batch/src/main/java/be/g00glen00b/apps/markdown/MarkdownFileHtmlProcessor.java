package be.g00glen00b.apps.markdown;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class MarkdownFileHtmlProcessor implements ItemProcessor<Resource, HtmlRendering> {
    private Parser parser;
    private HtmlRenderer htmlRenderer;

    @PostConstruct
    public void initialize() {
        parser = Parser.builder().build();
        htmlRenderer = HtmlRenderer.builder().build();
    }

    @Override
    public HtmlRendering process(Resource markdownResource) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(markdownResource.getInputStream())) {
            Node document = parser.parseReader(reader);
            return new HtmlRendering(markdownResource, htmlRenderer.render(document));
        }
    }
}
