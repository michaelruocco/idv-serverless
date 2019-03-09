package uk.co.mruoc.idv.awslambda.identity;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.awslambda.identity.IdentityNotFoundErrorHandler.IdentityNotFoundErrorItem;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityService.IdentityNotFoundException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class IdentityNotFoundErrorHandlerTest {

    private final JsonApiErrorHandler handler = new IdentityNotFoundErrorHandler();

    @Test
    public void shouldSupportIdentityNotFoundException() {
        assertThat(handler.getSupportedExceptions()).containsExactly(IdentityNotFoundException.class);
    }

    @Test
    public void shouldThrowClassCastExceptionIfInvalidExceptionTypeHandled() {
        final Exception invalidTypeException = new Exception("test");

        final Throwable error = catchThrowable(() -> handler.handle(invalidTypeException));

        assertThat(error).isInstanceOf(ClassCastException.class);
    }

    @Test
    public void shouldReturnJsonApiErrorDocumentForIdentityNotFoundException() {
        final Alias alias = new UkcCardholderIdAlias("12345678");
        final Exception exception = new IdentityNotFoundException(alias);

        final JsonApiErrorDocument document = handler.handle(exception);

        final JsonApiErrorItem expectedErrorItem = new IdentityNotFoundErrorItem(alias);
        assertThat(document.getErrors()).hasSize(1);
        assertThat(document.getErrors().get(0)).isEqualToComparingFieldByFieldRecursively(expectedErrorItem);
    }

}