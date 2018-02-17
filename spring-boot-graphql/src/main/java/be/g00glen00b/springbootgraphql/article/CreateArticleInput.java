package be.g00glen00b.springbootgraphql.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleInput {
    private String title;
    private String text;
    private Long authorId;
}
