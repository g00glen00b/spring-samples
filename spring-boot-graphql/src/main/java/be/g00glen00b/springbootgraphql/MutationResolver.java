package be.g00glen00b.springbootgraphql;

import be.g00glen00b.springbootgraphql.article.Article;
import be.g00glen00b.springbootgraphql.article.ArticleNotFoundException;
import be.g00glen00b.springbootgraphql.article.ArticleRepository;
import be.g00glen00b.springbootgraphql.article.CreateArticleInput;
import be.g00glen00b.springbootgraphql.article.UpdateArticleInput;
import be.g00glen00b.springbootgraphql.comment.Comment;
import be.g00glen00b.springbootgraphql.comment.CommentRepository;
import be.g00glen00b.springbootgraphql.comment.CreateCommentInput;
import be.g00glen00b.springbootgraphql.profile.CreateProfileInput;
import be.g00glen00b.springbootgraphql.profile.Profile;
import be.g00glen00b.springbootgraphql.profile.ProfileNotFoundException;
import be.g00glen00b.springbootgraphql.profile.ProfileRepository;
import be.g00glen00b.springbootgraphql.profile.UpdateProfileInput;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class MutationResolver implements GraphQLMutationResolver {
    private ArticleRepository articleRepository;
    private CommentRepository commentRepository;
    private ProfileRepository profileRepository;

    @Transactional
    public Article createArticle(CreateArticleInput input) {
        return articleRepository.saveAndFlush(new Article(null, input.getTitle(), input.getText(), input.getAuthorId()));
    }

    @Transactional
    public Article updateArticle(UpdateArticleInput input) {
        Article article = articleRepository
            .findById(input.getId())
            .orElseThrow(() -> new ArticleNotFoundException(input.getId()));
        article.setText(input.getText());
        article.setTitle(input.getTitle());
        return article;
    }

    @Transactional
    public int deleteArticle(Long id) {
        return articleRepository.deleteById(id);
    }

    @Transactional
    public Profile createProfile(CreateProfileInput input) {
        return profileRepository.saveAndFlush(new Profile(null, input.getUsername(), input.getBio()));
    }

    @Transactional
    public Profile updateProfile(UpdateProfileInput input) {
        Profile profile = profileRepository
            .findById(input.getId())
            .orElseThrow(() -> new ProfileNotFoundException(input.getId()));
        profile.setBio(input.getBio());
        return profile;
    }

    @Transactional
    public int deleteProfile(Long id) {
        return profileRepository.deleteById(id);
    }

    @Transactional
    public Comment createComment(CreateCommentInput input) {
        return commentRepository.saveAndFlush(new Comment(null, input.getText(), input.getArticleId(), input.getAuthorId()));
    }

    @Transactional
    public int deleteComment(Long id) {
        return commentRepository.deleteById(id);
    }
}
