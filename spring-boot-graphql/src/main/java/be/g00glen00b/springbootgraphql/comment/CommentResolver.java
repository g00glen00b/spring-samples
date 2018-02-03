package be.g00glen00b.springbootgraphql.comment;

import be.g00glen00b.springbootgraphql.profile.Profile;
import be.g00glen00b.springbootgraphql.profile.ProfileRepository;
import com.coxautodev.graphql.tools.GraphQLResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentResolver implements GraphQLResolver<Comment> {
    private ProfileRepository profileRepository;

    public Profile getAuthor(Comment comment) {
        return profileRepository.findOne(comment.getAuthorId());
    }
}
