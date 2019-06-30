package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.lockoutstate.model.DefaultLockoutState;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutState;
import uk.co.mruoc.idv.core.verificationcontext.service.CreateVerificationContextService.LockoutStateIsLockedException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class LockoutStateIsLockedErrorHandlerTest {

    private final JsonApiErrorHandler handler = new LockoutStatelsLockedErrorHandler();

    @Test
    public void shouldSupportException() {
        assertThat(handler.getSupportedExceptions()).containsExactly(LockoutStateIsLockedException.class);
    }

    @Test
    public void shouldReturnJsonApiErrorDocument() {
        final UUID idvId = UUID.randomUUID();
        final LockoutState lockoutState = mock(DefaultLockoutState.class);
        given(lockoutState.getIdvId()).willReturn(idvId);
        final Exception exception = new LockoutStateIsLockedException(lockoutState);

        final JsonApiErrorDocument document = handler.handle(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        final JsonApiErrorItem expectedItem = new LockoutStateIsLockedErrorItem(lockoutState);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(expectedItem);
    }

}
