package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import org.junit.Test;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationPolicyNotConfiguredForActivityErrorItemTest {

    private static final int UNPROCESSABLE_ENTITY = 422;

    private static final String CHANNEL_ID = "channelId";
    private static final String ACTIVITY_TYPE = "activityType";
    private static final String DETAIL = "No verification policies are configured for channel channelId and activity activityType";

    private final JsonApiErrorItem item = new VerificationPolicyNotConfiguredForActivityErrorItem(CHANNEL_ID, ACTIVITY_TYPE);

    @Test
    public void shouldReturnUnprocessableEntityStatusCode() {
        assertThat(item.getStatusCode()).isEqualTo(UNPROCESSABLE_ENTITY);
    }

    @Test
    public void shouldReturnNotFoundStatus() {
        assertThat(item.getStatus()).isEqualTo(Integer.toString(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldReturnTitle() {
        assertThat(item.getTitle()).isEqualTo("No verification policies configured");
    }

    @Test
    public void shouldReturnDetail() {
        assertThat(item.getDetail()).isEqualTo(DETAIL);
    }

    @Test
    public void shouldReturnMeta() {
        final Map<String, Object> meta = item.getMeta();

        assertThat(meta).hasSize(2);
        assertThat(meta).containsEntry("channelId", CHANNEL_ID);
        assertThat(meta).containsEntry("activityType", ACTIVITY_TYPE);
    }

}
