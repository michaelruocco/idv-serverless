package uk.co.mruoc.idv.core.identity.model.alias;

import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class IdvIdAliasTest {

    private final UUID VALUE = UUID.randomUUID();

    private final IdvIdAlias alias = new IdvIdAlias(VALUE.toString());

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
        assertThat(alias.getTypeName()).isEqualTo(IdvIdAliasType.NAME);
    }

    @Test
    public void isNotCardNumber() {
        assertThat(alias.isCardNumber()).isFalse();
    }

    @Test
    public void isNotSensitive() {
        assertThat(alias.isSensitive()).isFalse();
    }

    @Test
    public void shouldCreateRandomValueIfNotProvided() {
        final IdvIdAlias randomIdvId = new IdvIdAlias();

        assertThat(randomIdvId.getValueAsUuid()).isNotNull();
        assertThat(randomIdvId.getValue()).isNotNull();
    }

    @Test
    public void shouldPrintDetails() {
        final String expectedValue = String.format("DefaultAlias" +
                "(type=DefaultAliasType(name=IDV_ID), " +
                "format=CLEAR_TEXT, " +
                "value=%s)", VALUE);

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
