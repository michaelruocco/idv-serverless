package uk.co.mruoc.idv.app.identity.model.alias;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class IdvIdAliasTest {

    private final UUID VALUE = UUID.randomUUID();

    private final IdvIdAlias alias = new IdvIdAlias(VALUE);

    @Test
    public void shouldReturnUuidValue() {
        assertThat(alias.getValueAsUuid()).isEqualTo(VALUE);
    }

    @Test
    public void shouldReturnStringValue() {
        assertThat(alias.getValue()).isEqualTo(VALUE.toString());
    }

    @Test
    public void shouldHaveCorrectType() {
        assertThat(alias.getType()).isEqualTo(AliasType.IDV_ID);
    }

    @Test
    public void isNotCardNumber() {
        assertThat(alias.isCardNumber()).isFalse();
    }

    @Test
    public void shouldCreateRandomValueIfNotProvided() {
        final IdvIdAlias randomIdvId = new IdvIdAlias();

        assertThat(randomIdvId.getValueAsUuid()).isNotNull();
        assertThat(randomIdvId.getValue()).isNotNull();
    }

    @Test
    public void shouldPrintDetails() {
        final String expectedValue = String.format("IdvIdAlias" +
                "(super=AbstractAlias(type=IDV_ID, value=%s))", VALUE.toString());

        assertThat(alias.toString()).isEqualTo(expectedValue);
    }

    @Test
    public void testEquals() {
        final IdvIdAlias alias = new IdvIdAlias();
        final Alias sameAlias = new IdvIdAlias(alias.getValueAsUuid());
        final Alias differentAlias = new IdvIdAlias();

        assertThat(alias.equals(sameAlias)).isTrue();
        assertThat(alias.equals(differentAlias)).isFalse();
    }

}
