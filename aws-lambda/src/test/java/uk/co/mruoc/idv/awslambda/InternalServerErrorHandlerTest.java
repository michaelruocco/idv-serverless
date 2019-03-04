package uk.co.mruoc.idv.awslambda;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.InternalServerErrorHandler.InternalServerErrorItem;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import static org.assertj.core.api.Assertions.assertThat;

public class InternalServerErrorHandlerTest {

    private final JsonApiErrorHandler handler = new InternalServerErrorHandler();

    @Test
    public void shouldSupportException() {
        assertThat(handler.getSupportedExceptions()).containsExactly(Exception.class);
    }

    @Test
    public void shouldReturnJsonApiErrorDocumentForException() {
        final String message = "test-message";
        final Exception exception = new Exception(message);

        final JsonApiErrorDocument document = handler.handle(exception);

        assertThat(document.getErrors()).hasSize(1);
        assertThat(document.getErrors().get(0)).isEqualToComparingFieldByFieldRecursively(new InternalServerErrorItem(message));
    }

}
