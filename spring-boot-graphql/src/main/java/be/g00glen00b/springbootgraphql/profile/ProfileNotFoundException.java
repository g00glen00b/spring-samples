package be.g00glen00b.springbootgraphql.profile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class ProfileNotFoundException extends RuntimeException implements GraphQLError {
    private Long profileId;

    public ProfileNotFoundException(Long profileId) {
        this.profileId = profileId;
    }

    @Override
    public String getMessage() {
        return "Profile with ID " + profileId + " could not be found";
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Collections.singletonMap("profileId", profileId);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ValidationError;
    }
}
