package codes.dimitri.examples.contracttesting;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Orders API")
                .description("Contract testing examples — Orders API")
                .version("0.0.1-SNAPSHOT"));
    }
}