package be.g00glen00b.apps;

import be.g00glen00b.apps.user.User;
import be.g00glen00b.apps.user.UserInput;
import be.g00glen00b.apps.user.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.headers.ResponseHeadersSnippet;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureRestDocs(outputDir = "target/generated-sources/snippets")
public class SpringBootRestDocsApplicationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository repository;

    @Test
    public void findAllShouldReturnListOfUsers() throws Exception {
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Lists.newArrayList(
            new User(1L, "Doe", null, "John", LocalDate.of(2010, 1, 1), 0),
            new User(2L, "Doe", "Foo", "Jane", LocalDate.of(1999, 12, 31), 2))));
        mockMvc.perform(get("/api/user?page=2&size=5").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].id", is(1)))
            .andExpect(jsonPath("$.[0].lastName", is("Doe")))
            .andExpect(jsonPath("$.[0].middleName", nullValue()))
            .andExpect(jsonPath("$.[0].firstName", is("John")))
            .andExpect(jsonPath("$.[0].dateOfBirth", is("2010-01-01")))
            .andExpect(jsonPath("$.[0].siblings", is(0)))
            .andExpect(jsonPath("$.[1].id", is(2)))
            .andExpect(jsonPath("$.[1].lastName", is("Doe")))
            .andExpect(jsonPath("$.[1].middleName", is("Foo")))
            .andExpect(jsonPath("$.[1].firstName", is("Jane")))
            .andExpect(jsonPath("$.[1].dateOfBirth", is("1999-12-31")))
            .andExpect(jsonPath("$.[1].siblings", is(2)))
            .andExpect(header().longValue("X-Users-Total", 2L))
            .andDo(document("{method-name}", pageParameters(), userCollection(), pageHeaders()));
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(repository).findAll(captor.capture());
        assertThat(captor.getValue().getPageNumber()).isEqualTo(2);
        assertThat(captor.getValue().getPageSize()).isEqualTo(5);
    }

    @Test
    public void findAllShouldDefaultToPageOneAndSizeTen() throws Exception {
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Lists.newArrayList(
            new User(1L, "Doe", null, "John", LocalDate.of(2010, 1, 1), 0),
            new User(2L, "Doe", "Foo", "Jane", LocalDate.of(1999, 12, 31), 2))));
        mockMvc.perform(get("/api/user").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].id", is(1)))
            .andExpect(jsonPath("$.[0].lastName", is("Doe")))
            .andExpect(jsonPath("$.[0].middleName", nullValue()))
            .andExpect(jsonPath("$.[0].firstName", is("John")))
            .andExpect(jsonPath("$.[0].dateOfBirth", is("2010-01-01")))
            .andExpect(jsonPath("$.[0].siblings", is(0)))
            .andExpect(jsonPath("$.[1].id", is(2)))
            .andExpect(jsonPath("$.[1].lastName", is("Doe")))
            .andExpect(jsonPath("$.[1].middleName", is("Foo")))
            .andExpect(jsonPath("$.[1].firstName", is("Jane")))
            .andExpect(jsonPath("$.[1].dateOfBirth", is("1999-12-31")))
            .andExpect(jsonPath("$.[1].siblings", is(2)))
            .andExpect(header().longValue("X-Users-Total", 2L))
            .andDo(document("{method-name}", pageParameters(), userCollection()));
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(repository).findAll(captor.capture());
        assertThat(captor.getValue().getPageNumber()).isEqualTo(1);
        assertThat(captor.getValue().getPageSize()).isEqualTo(10);
    }

    @Test
    public void findAllShouldReturnErrorIfPageIsNegative() throws Exception {
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Lists.newArrayList(
            new User(1L, "Doe", null, "John", LocalDate.of(2010, 1, 1), 0),
            new User(2L, "Doe", "Foo", "Jane", LocalDate.of(1999, 12, 31), 2))));
        mockMvc.perform(get("/api/user?page=-1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].codes", containsInAnyOrder("findAll.page")))
            .andExpect(jsonPath("$.[0].message", is("Page number should be a positive number")))
            .andDo(document("{method-name}", pageParameters(), apiError()));
    }

    @Test
    public void findAllShouldReturnErrorIfPageSizeIsNegative() throws Exception {
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Lists.newArrayList(
            new User(1L, "Doe", null, "John", LocalDate.of(2010, 1, 1), 0),
            new User(2L, "Doe", "Foo", "Jane", LocalDate.of(1999, 12, 31), 2))));
        mockMvc.perform(get("/api/user?size=-1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].codes", containsInAnyOrder("findAll.size")))
            .andExpect(jsonPath("$.[0].message", is("Page size should be a positive number")))
            .andDo(document("{method-name}", pageParameters(), apiError()));
    }

    @Test
    public void saveShouldReturnUser() throws Exception {
        when(repository.saveAndFlush(any())).thenReturn(new User(3L, "Doe", "Bar", "Joe", LocalDate.of(2000, 1, 1), 4));
        mockMvc.perform(post("/api/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"lastName\":\"Doe\",\"middleName\":\"Bar\",\"firstName\":\"Joe\",\"dateOfBirth\":\"2000-01-01\",\"siblings\":4}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(3)))
            .andExpect(jsonPath("$.lastName", is("Doe")))
            .andExpect(jsonPath("$.middleName", is("Bar")))
            .andExpect(jsonPath("$.firstName", is("Joe")))
            .andExpect(jsonPath("$.dateOfBirth", is("2000-01-01")))
            .andExpect(jsonPath("$.siblings", is(4)))
            .andDo(document("{method-name}", userInput(), user()));
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repository).saveAndFlush(captor.capture());
        assertThat(captor.getValue().getId()).isNull();
        assertThat(captor.getValue().getLastName()).isEqualTo("Doe");
        assertThat(captor.getValue().getMiddleName()).isEqualTo("Bar");
        assertThat(captor.getValue().getFirstName()).isEqualTo("Joe");
        assertThat(captor.getValue().getDateOfBirth()).isEqualTo(LocalDate.of(2000, 1, 1));
        assertThat(captor.getValue().getSiblings()).isEqualTo(4);
    }

    @Test
    public void saveShouldReturnErrorIfLastNameIsEmpty() throws Exception {
        mockMvc.perform(post("/api/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"lastName\":null,\"middleName\":\"Bar\",\"firstName\":\"Joe\",\"dateOfBirth\":\"2000-01-01\",\"siblings\":4}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].message", is("Last name should not be empty")))
            .andExpect(jsonPath("$.[0].codes", containsInAnyOrder("NotNull", "NotNull.lastName", "NotNull.userInput.lastName", "NotNull.java.lang.String")))
            .andDo(document("{method-name}", userInput(), apiError()));
    }

    @Test
    public void saveShouldReturnErrorIfLastNameIsTooLong() throws Exception {
        mockMvc.perform(post("/api/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"lastName\":\"should be less than sixty characters otherwise we get an error\",\"middleName\":\"Bar\",\"firstName\":\"Joe\",\"dateOfBirth\":\"2000-01-01\",\"siblings\":4}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].message", is("Last name should be between 1 and 60 characters")))
            .andExpect(jsonPath("$.[0].codes", containsInAnyOrder("Size", "Size.lastName", "Size.userInput.lastName", "Size.java.lang.String")))
            .andDo(document("{method-name}", userInput(), apiError()));
    }

    @Test
    public void saveShouldReturnErrorIfMiddleNameIsTooLong() throws Exception {
        mockMvc.perform(post("/api/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"lastName\":\"Doe\",\"middleName\":\"should be less than sixty characters otherwise we get an error\",\"firstName\":\"Joe\",\"dateOfBirth\":\"2000-01-01\",\"siblings\":4}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].message", is("Middle name should be at most 60 characters")))
            .andExpect(jsonPath("$.[0].codes", containsInAnyOrder("Size", "Size.middleName", "Size.userInput.middleName", "Size.java.lang.String")))
            .andDo(document("{method-name}", userInput(), apiError()));
    }

    @Test
    public void saveShouldReturnErrorIfFirstNameIsEmpty() throws Exception {
        mockMvc.perform(post("/api/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"lastName\":\"Doe\",\"middleName\":\"Bar\",\"firstName\":null,\"dateOfBirth\":\"2000-01-01\",\"siblings\":4}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].message", is("First name should not be empty")))
            .andExpect(jsonPath("$.[0].codes", containsInAnyOrder("NotNull", "NotNull.firstName", "NotNull.userInput.firstName", "NotNull.java.lang.String")))
            .andDo(document("{method-name}", userInput(), apiError()));
    }

    @Test
    public void saveShouldReturnErrorIfFirstNameIsTooLong() throws Exception {
        mockMvc.perform(post("/api/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"lastName\":\"Doe\",\"middleName\":\"Bar\",\"firstName\":\"should be less than sixty characters otherwise we get an error\",\"dateOfBirth\":\"2000-01-01\",\"siblings\":4}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].message", is("First name should be between 1 and 60 characters")))
            .andExpect(jsonPath("$.[0].codes", containsInAnyOrder("Size", "Size.firstName", "Size.userInput.firstName", "Size.java.lang.String")))
            .andDo(document("{method-name}", userInput(), apiError()));
    }

    @Test
    public void saveShouldReturnErrorIfDateOfBirthIsEmpty() throws Exception {
        mockMvc.perform(post("/api/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"lastName\":\"Doe\",\"middleName\":\"Bar\",\"firstName\":\"Joe\",\"dateOfBirth\":null,\"siblings\":4}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].message", is("Date of birth should not be empty")))
            .andExpect(jsonPath("$.[0].codes", containsInAnyOrder("NotNull", "NotNull.dateOfBirth", "NotNull.userInput.dateOfBirth", "NotNull.java.time.LocalDate")))
            .andDo(document("{method-name}", userInput(), apiError()));
    }

    @Test
    public void saveShouldReturnErrorIfDateOfBirthIsInFuture() throws Exception {
        mockMvc.perform(post("/api/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"lastName\":\"Doe\",\"middleName\":\"Bar\",\"firstName\":\"Joe\",\"dateOfBirth\":\"2999-01-01\",\"siblings\":4}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].message", is("Date of birth should be in the past")))
            .andExpect(jsonPath("$.[0].codes", containsInAnyOrder("Past", "Past.dateOfBirth", "Past.userInput.dateOfBirth", "Past.java.time.LocalDate")))
            .andDo(document("{method-name}", userInput(), apiError()));
    }

    @Test
    public void saveShouldReturnErrorIfSiblingsIsNull() throws Exception {
        mockMvc.perform(post("/api/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"lastName\":\"Doe\",\"middleName\":\"Bar\",\"firstName\":\"Joe\",\"dateOfBirth\":\"2000-01-01\",\"siblings\":null}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].message", is("The amount of siblings should not be empty")))
            .andExpect(jsonPath("$.[0].codes", containsInAnyOrder("NotNull", "NotNull.siblings", "NotNull.userInput.siblings", "NotNull.java.lang.Integer")))
            .andDo(document("{method-name}", userInput(), apiError()));
    }

    @Test
    public void saveShouldReturnErrorIfSiblingsIsNegative() throws Exception {
        mockMvc.perform(post("/api/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"lastName\":\"Doe\",\"middleName\":\"Bar\",\"firstName\":\"Joe\",\"dateOfBirth\":\"2000-01-01\",\"siblings\":-2}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.[0].message", is("The amount of siblings should be positive")))
            .andExpect(jsonPath("$.[0].codes", containsInAnyOrder("PositiveOrZero", "PositiveOrZero.siblings", "PositiveOrZero.userInput.siblings", "PositiveOrZero.java.lang.Integer")))
            .andDo(document("{method-name}", userInput(), apiError()));
    }

    @Test
    public void findOneShouldReturnUser() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(new User(1L, "Doe", null, "John", LocalDate.of(2010, 1, 1), 0)));
        mockMvc.perform(get("/api/user/{id}", 1L).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.lastName", is("Doe")))
            .andExpect(jsonPath("$.middleName", nullValue()))
            .andExpect(jsonPath("$.firstName", is("John")))
            .andExpect(jsonPath("$.dateOfBirth", is("2010-01-01")))
            .andExpect(jsonPath("$.siblings", is(0)))
            .andDo(document("{method-name}", pathParameters(
                parameterWithName("id").description("The unique identifier of the user")
            ), user()));
    }

    @Test
    public void findOneShouldReturnErrorIfNotFound() throws Exception {
        when(repository.findById(-1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/user/{id}", -1L).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.[0].message", is("User with id '-1' is not found")))
            .andExpect(jsonPath("$.[0].codes", containsInAnyOrder("user.notfound")))
            .andDo(document("{method-name}", pathParameters(
                parameterWithName("id").description("The unique identifier of the user")
            ), apiError()));
    }

    private ResponseFieldsSnippet apiError() {
        return responseFields(
            fieldWithPath("[].codes").description("A list of technical codes describing the error"),
            fieldWithPath("[].message").description("A message describing the error").optional());
    }

    private ResponseFieldsSnippet user() {
        return responseFields(
            fieldWithPath("id").description("The unique identifier of the user"),
            fieldWithPath("lastName").description("The last name of the user"),
            fieldWithPath("middleName").description("The optional middle name of the user").optional(),
            fieldWithPath("firstName").description("The first name of the user"),
            fieldWithPath("dateOfBirth").description("The birthdate of the user in ISO 8601 format"),
            fieldWithPath("siblings").description("The amount of siblings the user has"));
    }

    private RequestFieldsSnippet userInput() {
        ConstraintDescriptions constraintDescriptions = new ConstraintDescriptions(UserInput.class);
        return requestFields(
            fieldWithPath("lastName").description("The last name of the user")
                .attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("lastName"))),
            fieldWithPath("middleName").description("The optional middle name of the user").optional()
                .attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("middleName"))),
            fieldWithPath("firstName").description("The first name of the user")
                .attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("firstName"))),
            fieldWithPath("dateOfBirth").description("The birthdate of the user in ISO 8601 format")
                .attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("dateOfBirth"))),
            fieldWithPath("siblings").description("The amount of siblings the user has")
                .attributes(key("constraints").value(constraintDescriptions.descriptionsForProperty("siblings"))));
    }

    private RequestParametersSnippet pageParameters() {
        return requestParameters(
            parameterWithName("page").description("The page to retrieve").optional(),
            parameterWithName("size").description("The number of elements within a single page").optional()
        );
    }

    private ResponseFieldsSnippet userCollection() {
        return responseFields(
            fieldWithPath("[].id").description("The unique identifier of the user"),
            fieldWithPath("[].lastName").description("The last name of the user"),
            fieldWithPath("[].middleName").description("The optional middle name of the user").optional(),
            fieldWithPath("[].firstName").description("The first name of the user"),
            fieldWithPath("[].dateOfBirth").description("The birthdate of the user in ISO 8601 format"),
            fieldWithPath("[].siblings").description("The amount of siblings the user has"));
    }

    private ResponseHeadersSnippet pageHeaders() {
        return responseHeaders(headerWithName("X-Users-Total").description("The total amount of users"));
    }
}
