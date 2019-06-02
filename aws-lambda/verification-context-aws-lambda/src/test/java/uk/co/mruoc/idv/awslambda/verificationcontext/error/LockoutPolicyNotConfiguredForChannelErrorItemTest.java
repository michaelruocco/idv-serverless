package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import org.junit.Test;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LockoutPolicyNotConfiguredForChannelErrorItemTest {

    private static final int UNPROCESSABLE_ENTITY = 422;

    private static final String CHANNEL_ID = "channelId";
    private static final String DETAIL = "No lockout policies are configured for channel channelId";

    private final JsonApiErrorItem item = new LockoutPolicyNotConfiguredForChannelErrorItem(CHANNEL_ID);

    @Test
    public void shouldReturnUnprocessableEntityStatusCode() {
        assertThat(item.getStatusCode()).isEqualTo(UNPROCESSABLE_ENTITY);
    }

    @Test
    public void shouldReturnUnprocessableEntityStatus() {
        assertThat(item.getStatus()).isEqualTo(Integer.toString(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldReturnTitle() {
        assertThat(item.getTitle()).isEqualTo("No lockout policies configured");
    }

    @Test
    public void shouldReturnDetail() {
        assertThat(item.getDetail()).isEqualTo(DETAIL);
    }

    @Test
    public void shouldReturnMeta() {
        final Map<String, Object> meta = item.getMeta();

        assertThat(meta).hasSize(1);
        assertThat(meta).containsEntry("channelId", CHANNEL_ID);
    }

}
