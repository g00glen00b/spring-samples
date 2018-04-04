package be.g00glen00b.apps.processing;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

@Component
public class MarkdownDocumentProcessor implements ItemProcessor<Resource, HtmlResource> {
    private Parser parser;
    private HtmlRenderer htmlRenderer;

    @PostConstruct
    public void initialize() {
        List<Extension> extensions = Collections.singletonList(TablesExtension.create());
        parser = Parser.builder().extensions(extensions).build();
        htmlRenderer = HtmlRenderer.builder().extensions(extensions).build();
    }

    @Override
    public HtmlResource process(Resource markdownResource) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(markdownResource.getInputStream())) {
            Node document = parser.parseReader(reader);
            return new HtmlResource(markdownResource, htmlRenderer.render(document));
        }
    }
}
