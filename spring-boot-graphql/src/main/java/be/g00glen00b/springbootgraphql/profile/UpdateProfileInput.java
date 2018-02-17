package be.g00glen00b.springbootgraphql.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileInput {
    private Long id;
    private String bio;
}
