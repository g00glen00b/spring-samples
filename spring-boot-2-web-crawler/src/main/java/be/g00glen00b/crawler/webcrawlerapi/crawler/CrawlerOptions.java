package be.g00glen00b.crawler.webcrawlerapi.crawler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CrawlerOptions {
    private String seed;
    private int depth;
    private int scheduleDelayMillis;
}
