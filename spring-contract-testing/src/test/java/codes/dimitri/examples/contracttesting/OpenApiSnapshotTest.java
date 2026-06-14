package codes.dimitri.examples.contracttesting;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.junit.jupiter.api.Test;
import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.openapitools.openapidiff.core.output.ConsoleRender;
import codes.dimitri.examples.contracttesting.order.OrderController;
import codes.dimitri.examples.contracttesting.order.OrderRepository;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.webmvc.api.OpenApiWebMvcResource;
import org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = OrderController.class)
@ImportAutoConfiguration({SpringDocConfiguration.class, SpringDocConfigProperties.class, SpringDocWebMvcConfiguration.class})
class OpenApiSnapshotTest {

    @MockitoBean
    private OrderRepository repository;
    @Autowired
    private OpenApiWebMvcResource openApiResource;
    @Value("classpath:snapshots/full-spec.json")
    private Resource snapshotSpec;

    @Test
    void openApiSpec_matchesSnapshot() throws Exception {
        byte[] specBytes = openApiResource.openapiJson(new MockHttpServletRequest(), "/v3/api-docs", Locale.ENGLISH);
        OpenAPI currentSpec = new OpenAPIV3Parser().readContents(new String(specBytes, StandardCharsets.UTF_8)).getOpenAPI();

        String snapshotSpecString = snapshotSpec.getContentAsString(StandardCharsets.UTF_8);
        OpenAPI snapshotSpec = new OpenAPIV3Parser().readContents(snapshotSpecString).getOpenAPI();

        ChangedOpenApi diff = OpenApiCompare.fromSpecifications(snapshotSpec, currentSpec);
        assertThat(diff.isUnchanged())
            .withFailMessage(() -> "OpenAPI spec has drifted from snapshot:\n" + new ConsoleRender().render(diff))
            .isTrue();
    }
}
