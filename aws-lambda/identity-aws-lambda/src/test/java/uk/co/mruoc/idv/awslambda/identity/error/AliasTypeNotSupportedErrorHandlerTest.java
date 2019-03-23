package uk.co.mruoc.idv.awslambda.identity.error;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderService.AliasTypeNotSupportedException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasTypeNotSupportedErrorHandlerTest {

    private final JsonApiErrorHandler handler = new AliasTypeNotSupportedErrorHandler();

    @Test
    public void shouldSupportIdentityRequestInvalidException() {
        assertThat(handler.getSupportedExceptions()).containsExactly(AliasTypeNotSupportedException.class);
    }

    @Test
    public void shouldReturnJsonApiErrorDocument() {
        final String aliasType = "aliasType";
        final String channelId = "channelId";
        final Exception exception = new AliasTypeNotSupportedException(aliasType, channelId);

        final JsonApiErrorDocument document = handler.handle(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        final JsonApiErrorItem item = new AliasTypeNotSupportedErrorItem(aliasType, channelId);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(item);
    }

}
