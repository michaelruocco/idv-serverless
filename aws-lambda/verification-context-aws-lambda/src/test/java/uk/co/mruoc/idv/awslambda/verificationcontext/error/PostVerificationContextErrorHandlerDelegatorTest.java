package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.ErrorHandlerDelegator;
import uk.co.mruoc.idv.awslambda.InternalServerErrorHandler.InternalServerErrorItem;
import uk.co.mruoc.idv.awslambda.verificationcontext.VerificationContextRequestExtractor.InvalidVerificationContextRequestException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostVerificationContextErrorHandlerDelegatorTest {

    private final ErrorHandlerDelegator delegator = new PostVerificationContextErrorHandlerDelegator();

    @Test
    public void shouldConvertInvalidVerificationContextRequestExceptionToErrorDocument() {
        final Exception exception = new InvalidVerificationContextRequestException(new Exception("message"));

        final JsonApiErrorDocument document = delegator.toDocument(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(new InvalidVerificationContextRequestErrorItem(exception.getMessage()));
    }

    @Test
    public void shouldConvertAnyOtherExceptionToInternalServerErrorDocument() {
        final String message = "test-message";

        final JsonApiErrorDocument document = delegator.toDocument(new Exception(message));

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(new InternalServerErrorItem(message));
    }

}
