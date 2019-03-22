package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.awslambda.verificationcontext.VerificationContextRequestExtractor.InvalidVerificationContextRequestException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidVerificationContextRequestErrorHandlerTest {

    private final JsonApiErrorHandler handler = new InvalidVerificationContextRequestErrorHandler();

    @Test
    public void shouldSupportIdentityRequestInvalidException() {
        assertThat(handler.getSupportedExceptions()).containsExactly(InvalidVerificationContextRequestException.class);
    }

    @Test
    public void shouldReturnJsonApiErrorDocument() {
        final Exception cause = new Exception("error");
        final Exception exception = new InvalidVerificationContextRequestException(cause);

        final JsonApiErrorDocument document = handler.handle(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        final JsonApiErrorItem expectedItem = new InvalidVerificationContextRequestErrorItem(exception.getMessage());
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(expectedItem);
    }

}
