package codes.dimitri.examples.contracttesting;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.openapitools.openapidiff.core.output.ConsoleRender;
import codes.dimitri.examples.contracttesting.order.OrderController;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import codes.dimitri.examples.contracttesting.order.OrderRepository;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.configuration.SpringDocPageableConfiguration;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.webmvc.api.OpenApiWebMvcResource;
import org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = OrderController.class)
@ImportAutoConfiguration({
    SpringDocConfiguration.class,
    SpringDocConfigProperties.class,
    SpringDocWebMvcConfiguration.class,
    SpringDocPageableConfiguration.class
})
class OpenApiSnapshotTest {

    @MockitoBean
    private OrderRepository repository;
    @Autowired
    private OpenApiWebMvcResource openApiResource;
    @Value("classpath:snapshots/full-spec.json")
    private Resource fullSnapshotSpec;
    @Value("classpath:snapshots/partial-spec.json")
    private Resource partialSnapshotSpec;

    private OpenAPI currentSpec;

    @BeforeEach
    void setUp() throws Exception {
        var specBytes = openApiResource.openapiJson(new MockHttpServletRequest(), "/v3/api-docs", Locale.ENGLISH);
        currentSpec = new OpenAPIV3Parser().readContents(new String(specBytes, StandardCharsets.UTF_8)).getOpenAPI();
    }

    private static String renderDiff(ChangedOpenApi diff) {
        var out = new ByteArrayOutputStream();
        new ConsoleRender().render(diff, new OutputStreamWriter(out, StandardCharsets.UTF_8));
        return out.toString(StandardCharsets.UTF_8);
    }

    @Test
    void regenerateFullSnapshot() throws Exception {
        var specBytes = openApiResource.openapiJson(new MockHttpServletRequest(), "/v3/api-docs", Locale.ENGLISH);
        var mapper = new ObjectMapper();
        var prettySpec = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(specBytes));
        Files.writeString(Path.of("src/test/resources/snapshots/full-spec.json"), prettySpec);
    }

    @Test
    void openApiSpec_matchesSnapshot() throws Exception {
        var snapshotSpec = new OpenAPIV3Parser().readContents(fullSnapshotSpec.getContentAsString(StandardCharsets.UTF_8)).getOpenAPI();

        ChangedOpenApi diff = OpenApiCompare.fromSpecifications(snapshotSpec, currentSpec);
        assertThat(diff.isUnchanged())
            .withFailMessage(() -> "OpenAPI spec has drifted from snapshot:\n" + renderDiff(diff))
            .isTrue();
    }

    @Test
    void openApiSpec_isCompatibleWithPartialSnapshot() throws Exception {
        var partialSnapshot = new OpenAPIV3Parser().readContents(partialSnapshotSpec.getContentAsString(StandardCharsets.UTF_8)).getOpenAPI();

        ChangedOpenApi diff = OpenApiCompare.fromSpecifications(partialSnapshot, currentSpec);
        assertThat(diff.isCompatible())
            .withFailMessage(() -> "OpenAPI spec broke compatibility with partial snapshot:\n" + renderDiff(diff))
            .isTrue();
    }
}
