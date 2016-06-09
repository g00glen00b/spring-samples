package be.g00glen00b;

import java.io.IOException;
import io.github.robwin.swagger.test.SwaggerAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootSwaggerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class SpringBootSwaggerApplicationTests {
    @Value("http://localhost:${local.server.port}/v2/api-docs?group=task-api")
    private String swaggerApi;

    @Test
    public void apiMatchesDefinition() throws IOException {
        SwaggerAssertions.assertThat(swaggerApi).isEqualTo(new ClassPathResource("swagger.yml").getFile().getPath());
    }
}