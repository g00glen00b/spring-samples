package be.g00glen00b.dto;

import java.time.LocalDate;
import java.util.List;

public class BlogpostDTO {
    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private LocalDate date;

    public BlogpostDTO(Long id, String title, String content, List<String> tags, LocalDate date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.date = date;
    }

    public BlogpostDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
