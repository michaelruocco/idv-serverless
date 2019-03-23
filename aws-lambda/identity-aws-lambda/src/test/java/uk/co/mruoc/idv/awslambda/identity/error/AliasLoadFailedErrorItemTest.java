package uk.co.mruoc.idv.awslambda.identity.error;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasLoadFailedErrorItemTest {

    private static final int UNPROCESSABLE_ENTITY = 422;

    private static final Alias ALIAS = new DefaultAlias(new DefaultAliasType("type"), "format", "value");

    private final JsonApiErrorItem item = new AliasLoadFailedErrorItem(ALIAS);

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
        assertThat(item.getTitle()).isEqualTo("Failed to load alias");
    }

    @Test
    public void shouldReturnDetail() {
        assertThat(item.getDetail()).isEqualTo("Failed to load alias " + ALIAS.toString());
    }

    @Test
    public void shouldReturnMeta() {
        final Map<String, Object> meta = item.getMeta();

        assertThat(meta).hasSize(1);
        assertThat(meta).containsEntry("alias", ALIAS);
    }

}
