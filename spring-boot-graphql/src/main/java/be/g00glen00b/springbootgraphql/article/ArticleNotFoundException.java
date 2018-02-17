package be.g00glen00b.springbootgraphql.article;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class ArticleNotFoundException extends RuntimeException implements GraphQLError {
    private Long articleId;

    public ArticleNotFoundException(Long articleId) {
        this.articleId = articleId;
    }

    @Override
    public String getMessage() {
        return "Article with ID " + articleId + " could not be found";
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ValidationError;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Collections.singletonMap("articleId", articleId);
    }
}
