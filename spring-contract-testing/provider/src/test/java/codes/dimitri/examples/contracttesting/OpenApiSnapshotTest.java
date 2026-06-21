package codes.dimitri.examples.contracttesting;

import com.fasterxml.jackson.databind.ObjectWriter;
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
import org.springframework.core.io.WritableResource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = {
    OrderController.class
})
@ImportAutoConfiguration({
    SpringDocConfiguration.class,
    SpringDocConfigProperties.class,
    SpringDocWebMvcConfiguration.class,
    SpringDocPageableConfiguration.class,
    OpenApiConfiguration.class
})
class OpenApiSnapshotTest {

    @MockitoBean
    private OrderRepository repository;
    @Autowired
    private OpenApiWebMvcResource openApiResource;
    @Value("file:src/test/snapshots/full-spec.json")
    private WritableResource fullSnapshotSpec;
    @Value("file:src/test/snapshots/partial-spec.json")
    private WritableResource partialSnapshotSpec;
    private ObjectMapper objectMapper;
    private ObjectWriter objectWriter;
    private OpenAPIV3Parser parser;
    private ConsoleRender consoleRender;
    private OpenAPI currentSpec;

    @BeforeEach
    void setUp() throws Exception {
        parser = new OpenAPIV3Parser();
        objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        consoleRender = new ConsoleRender();
        var specBytes = openApiResource.openapiJson(new MockHttpServletRequest(), "/v3/api-docs", Locale.ENGLISH);
        currentSpec = parser.readContents(new String(specBytes, StandardCharsets.UTF_8)).getOpenAPI();
    }

    private String renderDiff(ChangedOpenApi diff) {
        var out = new ByteArrayOutputStream();
        consoleRender.render(diff, new OutputStreamWriter(out, StandardCharsets.UTF_8));
        return out.toString(StandardCharsets.UTF_8);
    }

    @Test
    void regenerateFullSnapshot() throws Exception {
        var specBytes = openApiResource.openapiJson(new MockHttpServletRequest(), "/v3/api-docs", Locale.ENGLISH);
        var prettySpec = objectWriter.writeValueAsString(objectMapper.readTree(specBytes));
        try (var out = fullSnapshotSpec.getOutputStream()) {
            out.write(prettySpec.getBytes(StandardCharsets.UTF_8));
        }
    }

    @Test
    void openApiSpec_matchesSnapshot() throws Exception {
        var snapshotSpec = parser.readContents(fullSnapshotSpec.getContentAsString(StandardCharsets.UTF_8)).getOpenAPI();
        var diff = OpenApiCompare.fromSpecifications(snapshotSpec, currentSpec);

        assertThat(diff.isUnchanged())
            .withFailMessage(() -> "OpenAPI spec has drifted from snapshot:\n" + renderDiff(diff))
            .isTrue();
    }

    @Test
    void openApiSpec_isCompatibleWithPartialSnapshot() throws Exception {
        var partialSnapshot = new OpenAPIV3Parser().readContents(partialSnapshotSpec.getContentAsString(StandardCharsets.UTF_8)).getOpenAPI();
        var diff = OpenApiCompare.fromSpecifications(partialSnapshot, currentSpec);

        assertThat(diff.isCompatible())
            .withFailMessage(() -> "OpenAPI spec broke compatibility with partial snapshot:\n" + renderDiff(diff))
            .isTrue();
    }
}
