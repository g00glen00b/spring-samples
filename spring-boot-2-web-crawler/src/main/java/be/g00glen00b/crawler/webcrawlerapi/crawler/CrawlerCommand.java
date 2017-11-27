package be.g00glen00b.crawler.webcrawlerapi.crawler;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CrawlerCommand {
    private String seed;
    private int depth;
}
