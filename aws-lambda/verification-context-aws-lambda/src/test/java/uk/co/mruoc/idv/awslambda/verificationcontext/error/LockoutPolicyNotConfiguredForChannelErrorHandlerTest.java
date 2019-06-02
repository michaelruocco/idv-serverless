package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutPoliciesService.LockoutPolicyNotConfiguredForChannelException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LockoutPolicyNotConfiguredForChannelErrorHandlerTest {

    private final JsonApiErrorHandler handler = new LockoutPolicyNotConfiguredForChannelErrorHandler();

    @Test
    public void shouldSupportException() {
        assertThat(handler.getSupportedExceptions()).containsExactly(LockoutPolicyNotConfiguredForChannelException.class);
    }

    @Test
    public void shouldReturnJsonApiErrorDocument() {
        final String channelId = "channelId";
        final Exception exception = new LockoutPolicyNotConfiguredForChannelException(channelId);

        final JsonApiErrorDocument document = handler.handle(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        final JsonApiErrorItem expectedItem = new LockoutPolicyNotConfiguredForChannelErrorItem(channelId);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(expectedItem);
    }

}
