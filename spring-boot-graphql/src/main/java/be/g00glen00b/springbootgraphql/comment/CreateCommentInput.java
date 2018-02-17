package be.g00glen00b.springbootgraphql.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentInput {
    private String text;
    private Long authorId;
    private Long articleId;
}
