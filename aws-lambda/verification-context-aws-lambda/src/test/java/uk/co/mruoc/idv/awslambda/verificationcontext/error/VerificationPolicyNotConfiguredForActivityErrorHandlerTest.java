package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.JsonApiErrorHandler;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies.VerificationPolicyNotConfiguredForActivityException;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationPolicyNotConfiguredForActivityErrorHandlerTest {

    private final JsonApiErrorHandler handler = new VerificationPolicyNotConfiguredForActivityErrorHandler();

    @Test
    public void shouldSupportException() {
        assertThat(handler.getSupportedExceptions()).containsExactly(VerificationPolicyNotConfiguredForActivityException.class);
    }

    @Test
    public void shouldReturnJsonApiErrorDocument() {
        final String channelId = "channelId";
        final String activityType = "activityType";
        final Exception exception = new VerificationPolicyNotConfiguredForActivityException(channelId, activityType);

        final JsonApiErrorDocument document = handler.handle(exception);

        final List<JsonApiErrorItem> errors = document.getErrors();
        assertThat(errors).hasSize(1);
        final JsonApiErrorItem expectedItem = new VerificationPolicyNotConfiguredForActivityErrorItem(channelId, activityType);
        assertThat(errors.get(0)).isEqualToComparingFieldByFieldRecursively(expectedItem);
    }

}
