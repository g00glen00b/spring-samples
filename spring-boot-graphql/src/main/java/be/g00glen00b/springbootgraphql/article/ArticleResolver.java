package be.g00glen00b.springbootgraphql.article;

import java.util.List;
import be.g00glen00b.springbootgraphql.comment.Comment;
import be.g00glen00b.springbootgraphql.comment.CommentRepository;
import be.g00glen00b.springbootgraphql.profile.Profile;
import be.g00glen00b.springbootgraphql.profile.ProfileRepository;
import com.coxautodev.graphql.tools.GraphQLResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ArticleResolver implements GraphQLResolver<Article> {
    private CommentRepository commentRepository;
    private ProfileRepository profileRepository;

    public Profile getAuthor(Article article) {
        return profileRepository.findOne(article.getAuthorId());
    }

    public List<Comment> getComments(Article article) {
        return commentRepository.findByArticleId(article.getId());
    }
}
