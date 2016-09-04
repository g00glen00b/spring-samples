package be.g00glen00b.dto;

import java.util.List;

public class BlogpostsDTO {
    private int offset;
    private int limit;
    private long totalResults;
    private List<BlogpostDTO> results;

    public BlogpostsDTO(int offset, int limit, long totalResults, List<BlogpostDTO> results) {
        this.offset = offset;
        this.limit = limit;
        this.totalResults = totalResults;
        this.results = results;
    }

    public BlogpostsDTO() {
    }

    public int getOffset() {
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTotalResults() {
        return totalResults;
    }
    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public List<BlogpostDTO> getResults() {
        return results;
    }
    public void setResults(List<BlogpostDTO> results) {
        this.results = results;
    }
}
