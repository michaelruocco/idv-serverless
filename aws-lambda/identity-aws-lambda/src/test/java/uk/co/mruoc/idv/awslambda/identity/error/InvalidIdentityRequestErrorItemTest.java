package uk.co.mruoc.idv.awslambda.identity.error;

import org.junit.Test;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidIdentityRequestErrorItemTest {

    private static final int BAD_REQUEST = 400;

    private final JsonApiErrorItem item = new InvalidIdentityRequestErrorItem();

    @Test
    public void shouldReturnNotFoundStatusCode() {
        assertThat(item.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void shouldReturnNotFoundStatus() {
        assertThat(item.getStatus()).isEqualTo(Integer.toString(BAD_REQUEST));
    }

    @Test
    public void shouldReturnTitle() {
        assertThat(item.getTitle()).isEqualTo("Bad Request");
    }

    @Test
    public void shouldReturnDetail() {
        assertThat(item.getDetail()).isEqualTo("Either IDV ID or aliasType and aliasValue must be provided");
    }

    @Test
    public void shouldReturnMeta() {
        assertThat(item.getMeta()).isEmpty();
    }

}
