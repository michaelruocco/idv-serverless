package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import org.junit.Test;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidVerificationContextRequestErrorItemTest {

    private static final int BAD_REQUEST = 400;

    private static final String VALUE = "value";
    private static final String DETAIL = "Verification context request is invalid: value";

    private final JsonApiErrorItem item = new InvalidVerificationContextRequestErrorItem(VALUE);

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
        assertThat(item.getDetail()).isEqualTo(DETAIL);
    }

    @Test
    public void shouldReturnMeta() {
        assertThat(item.getMeta()).isEmpty();
    }

}
