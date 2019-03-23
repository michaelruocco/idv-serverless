package uk.co.mruoc.idv.awslambda.identity.error;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.ErrorHandlerDelegator;
import uk.co.mruoc.idv.awslambda.InternalServerErrorHandler.InternalServerErrorItem;
import uk.co.mruoc.idv.awslambda.identity.GetIdentityRequestValidator.IdentityRequestInvalidException;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias.IdvIdNotValidUuidException;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityService.IdentityNotFoundException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetIdentityErrorHandlerDelegatorTest {

    private final ErrorHandlerDelegator delegator = new GetIdentityErrorHandlerDelegator();

    @Test
    public void shouldConvertIdentityNotFoundExceptionToErrorDocument() {
        final Alias alias = new UkcCardholderIdAlias("12345678");
        final Exception exception = new IdentityNotFoundException(alias);

        final JsonApiErrorDocument document = delegator.toDocument(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(new IdentityNotFoundErrorItem(alias));
    }

    @Test
    public void shouldConvertIdentityRequestInvalidExceptionToErrorDocument() {
        final Exception exception = new IdentityRequestInvalidException();

        final JsonApiErrorDocument document = delegator.toDocument(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(new InvalidIdentityRequestErrorItem());
    }

    @Test
    public void shouldConvertIdvIdNotValidUuidExceptionToErrorDocument() {
        final String value = "value";
        final Exception exception = new IdvIdNotValidUuidException(value, new Exception());

        final JsonApiErrorDocument document = delegator.toDocument(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(new InvalidIdvIdErrorItem(value));
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
