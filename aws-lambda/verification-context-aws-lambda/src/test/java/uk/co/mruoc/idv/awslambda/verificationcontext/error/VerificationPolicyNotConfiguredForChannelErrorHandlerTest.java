package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationPoliciesService.VerificationPolicyNotConfiguredForChannelException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationPolicyNotConfiguredForChannelErrorHandlerTest {

    private final JsonApiErrorHandler handler = new VerificationPolicyNotConfiguredForChannelErrorHandler();

    @Test
    public void shouldSupportIdentityRequestInvalidException() {
        assertThat(handler.getSupportedExceptions()).containsExactly(VerificationPolicyNotConfiguredForChannelException.class);
    }

    @Test
    public void shouldReturnJsonApiErrorDocument() {
        final String channelId = "channelId";
        final Exception exception = new VerificationPolicyNotConfiguredForChannelException(channelId);

        final JsonApiErrorDocument document = handler.handle(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        final JsonApiErrorItem expectedItem = new VerificationPolicyNotConfiguredForChannelErrorItem(channelId);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(expectedItem);
    }

}
