package uk.co.mruoc.idv.awslambda.identity.error;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.service.AliasLoadFailedException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class AliasLoadFailedErrorHandlerTest {

    private final JsonApiErrorHandler handler = new AliasLoadFailedErrorHandler();

    @Test
    public void shouldSupportIdentityRequestInvalidException() {
        assertThat(handler.getSupportedExceptions()).containsExactly(AliasLoadFailedException.class);
    }

    @Test
    public void shouldReturnJsonApiErrorDocument() {
        final Alias alias = mock(Alias.class);
        final Exception exception = new AliasLoadFailedException(alias);

        final JsonApiErrorDocument document = handler.handle(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        final JsonApiErrorItem item = new AliasLoadFailedErrorItem(alias);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(item);
    }

}
