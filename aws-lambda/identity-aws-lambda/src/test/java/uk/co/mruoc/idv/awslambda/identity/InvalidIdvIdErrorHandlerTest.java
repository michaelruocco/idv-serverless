package uk.co.mruoc.idv.awslambda.identity;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.awslambda.identity.InvalidIdvIdErrorHandler.InvalidIdvIdErrorItem;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias.IdvIdNotValidUuidException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidIdvIdErrorHandlerTest {

    private final JsonApiErrorHandler handler = new InvalidIdvIdErrorHandler();

    @Test
    public void shouldSupportIdentityRequestInvalidException() {
        assertThat(handler.getSupportedExceptions()).containsExactly(IdvIdNotValidUuidException.class);
    }

    @Test
    public void shouldReturnJsonApiErrorDocument() {
        final String value = "value";
        final Exception exception = new IdvIdNotValidUuidException(value, new Exception());

        final JsonApiErrorDocument document = handler.handle(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(new InvalidIdvIdErrorItem(value));
    }

}
