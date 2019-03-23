package uk.co.mruoc.idv.awslambda.identity.error;

import org.junit.Test;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasTypeNotSupportedErrorItemTest {

    private static final int UNPROCESSABLE_ENTITY = 422;

    private static final String ALIAS_TYPE = "aliasType";
    private static final String CHANNEL_ID = "channelId";

    private final JsonApiErrorItem item = new AliasTypeNotSupportedErrorItem(ALIAS_TYPE, CHANNEL_ID);

    @Test
    public void shouldReturnNotFoundStatusCode() {
        assertThat(item.getStatusCode()).isEqualTo(UNPROCESSABLE_ENTITY);
    }

    @Test
    public void shouldReturnNotFoundStatus() {
        assertThat(item.getStatus()).isEqualTo(Integer.toString(UNPROCESSABLE_ENTITY));
    }

    @Test
    public void shouldReturnTitle() {
        assertThat(item.getTitle()).isEqualTo("Alias type not supported");
    }

    @Test
    public void shouldReturnDetail() {
        assertThat(item.getDetail()).isEqualTo("Alias type aliasType is not supported for channel channelId");
    }

    @Test
    public void shouldReturnMeta() {
        final Map<String, Object> meta = item.getMeta();

        assertThat(meta).hasSize(2);
        assertThat(meta).containsEntry("aliasType", ALIAS_TYPE);
        assertThat(meta).containsEntry("channelId", CHANNEL_ID);
    }

}
