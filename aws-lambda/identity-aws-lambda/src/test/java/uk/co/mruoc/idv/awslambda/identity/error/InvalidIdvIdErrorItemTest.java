package uk.co.mruoc.idv.awslambda.identity.error;

import org.junit.Test;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidIdvIdErrorItemTest {

    private static final int BAD_REQUEST = 400;

    private static final String VALUE = "value";
    private static final String DETAIL = "IDV ID value is invalid value it must be a valid UUID";

    private final JsonApiErrorItem item = new InvalidIdvIdErrorItem(VALUE);

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
        assertThat(item.getTitle()).isEqualTo("Invalid IDV ID");
    }

    @Test
    public void shouldReturnDetail() {
        assertThat(item.getDetail()).isEqualTo(DETAIL);
    }

    @Test
    public void shouldReturnMeta() {
        final Map<String, Object> meta = item.getMeta();

        assertThat(meta).hasSize(1);
        assertThat(meta).containsEntry("value", VALUE);
    }

}
