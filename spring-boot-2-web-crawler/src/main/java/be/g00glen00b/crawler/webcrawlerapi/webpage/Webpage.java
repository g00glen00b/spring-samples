package be.g00glen00b.crawler.webcrawlerapi.webpage;


import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Webpage {
    @Id
    private String url;
    private String title;
    @JsonIgnore
    private String content;
    private LocalDateTime lastCrawled;
}
