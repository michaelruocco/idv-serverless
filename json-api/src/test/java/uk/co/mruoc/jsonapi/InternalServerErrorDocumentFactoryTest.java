package uk.co.mruoc.jsonapi;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InternalServerErrorDocumentFactoryTest {

    private static final int STATUS_CODE = 500;

    private final JsonApiErrorDocumentFactory factory = new InternalServerErrorDocumentFactory();

    @Test
    public void shouldSupportException() {
        assertThat(factory.getSupportedExceptions()).containsExactly(Exception.class);
    }

    @Test
    public void shouldBuildErrorDocument() {
        final String message = "test-message";

        final JsonApiErrorDocument document = factory.build(new Exception(message));

        assertThat(document.getStatusCode()).isEqualTo(STATUS_CODE);
        final List<JsonApiErrorItem> errors = document.getData();
        assertThat(errors).hasSize(1);
        final JsonApiErrorItem error = errors.get(0);
        assertThat(error.getStatusCode()).isEqualTo(STATUS_CODE);
        assertThat(error.getStatus()).isEqualTo(Integer.toString(STATUS_CODE));
        assertThat(error.getId()).isNotNull();
        assertThat(error.getCode()).isEqualTo("INTERNAL_SERVER_ERROR");
        assertThat(error.getTitle()).isEqualTo("Internal server error");
        assertThat(error.getDetail()).isEqualTo(message);
        assertThat(error.getMeta()).isNull();
    }

    @Test
    public void shouldHaveNoArgsConstructorRequiredByJackson() {
        assertThat(new JsonApiErrorItem()).isNotNull();
    }

}