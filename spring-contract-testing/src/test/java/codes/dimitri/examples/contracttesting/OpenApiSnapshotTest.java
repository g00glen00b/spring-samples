package codes.dimitri.examples.contracttesting;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.junit.jupiter.api.Test;
import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.openapitools.openapidiff.core.output.ConsoleRender;
import org.springdoc.webmvc.api.OpenApiWebMvcResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OpenApiSnapshotTest {

    @Autowired
    private OpenApiWebMvcResource openApiResource;

    @Test
    void openApiSpec_matchesSnapshot() throws Exception {
        byte[] specBytes = openApiResource.openapiJson(new MockHttpServletRequest(), "/v3/api-docs", Locale.ENGLISH);
        OpenAPI currentSpec = new OpenAPIV3Parser().readContents(new String(specBytes, StandardCharsets.UTF_8)).getOpenAPI();

        URL snapshotUrl = getClass().getClassLoader().getResource("snapshots/full-spec.json");
        OpenAPI snapshotSpec = new OpenAPIV3Parser().read(snapshotUrl.toString());

        ChangedOpenApi diff = OpenApiCompare.fromSpecifications(snapshotSpec, currentSpec);
        assertThat(diff.isUnchanged())
            .withFailMessage(() -> "OpenAPI spec has drifted from snapshot:\n" + new ConsoleRender().render(diff))
            .isTrue();
    }
}
