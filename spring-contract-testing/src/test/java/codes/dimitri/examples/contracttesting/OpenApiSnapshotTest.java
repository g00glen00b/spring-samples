package codes.dimitri.examples.contracttesting;

import org.junit.jupiter.api.Test;
import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.openapitools.openapidiff.core.output.ConsoleRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiSnapshotTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void openApiSpec_matchesSnapshot() throws Exception {
        String currentSpec = mockMvc.perform(get("/v3/api-docs"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString(StandardCharsets.UTF_8);

        String snapshotSpec;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("snapshots/full-spec.json")) {
            snapshotSpec = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }

        ChangedOpenApi diff = OpenApiCompare.fromContents(snapshotSpec, currentSpec);

        assertThat(diff.isUnchanged())
            .withFailMessage(() -> "OpenAPI spec has drifted from snapshot:\n" + new ConsoleRender().render(diff))
            .isTrue();
    }
}
