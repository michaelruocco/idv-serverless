package uk.co.mruoc.idv.awslambda.identity;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.awslambda.identity.GetIdentityRequestValidator.IdentityRequestInvalidException;
import uk.co.mruoc.idv.awslambda.identity.InvalidIdentityRequestErrorHandler.InvalidIdentityRequestErrorItem;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidIdentityRequestErrorHandlerTest {

    private final JsonApiErrorHandler handler = new InvalidIdentityRequestErrorHandler();

    @Test
    public void shouldSupportIdentityRequestInvalidException() {
        assertThat(handler.getSupportedExceptions()).containsExactly(IdentityRequestInvalidException.class);
    }

    @Test
    public void shouldReturnJsonApiErrorDocument() {
        final Exception exception = new IdentityRequestInvalidException();

        final JsonApiErrorDocument document = handler.handle(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(new InvalidIdentityRequestErrorItem());
    }

}
