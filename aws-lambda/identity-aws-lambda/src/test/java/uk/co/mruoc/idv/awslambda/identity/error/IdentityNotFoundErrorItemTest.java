package uk.co.mruoc.idv.awslambda.identity.error;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class IdentityNotFoundErrorItemTest {

    private static final int NOT_FOUND = 404;

    private final Alias alias = new DefaultAlias(AliasType.toAliasType("type"), "format", "value");

    private final JsonApiErrorItem item = new IdentityNotFoundErrorItem(alias);

    @Test
    public void shouldReturnNotFoundStatusCode() {
        assertThat(item.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    public void shouldReturnNotFoundStatus() {
        assertThat(item.getStatus()).isEqualTo(Integer.toString(NOT_FOUND));
    }

    @Test
    public void shouldReturnTitle() {
        assertThat(item.getTitle()).isEqualTo("Identity not found");
    }

    @Test
    public void shouldReturnDetail() {
        assertThat(item.getDetail()).isEqualTo("Identity not found using alias type type and format value value");
    }

    @Test
    public void shouldReturnMeta() {
        final Map<String, Object> meta = item.getMeta();

        assertThat(meta).hasSize(1);
        assertThat(meta).containsEntry("alias", alias);
    }

}
