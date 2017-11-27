package be.g00glen00b.crawler.webcrawlerapi.crawler;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrawlerResult {
    private String url;
    private String title;
    private String content;
    private List<String> hyperlinks;
    private int depth;
}
