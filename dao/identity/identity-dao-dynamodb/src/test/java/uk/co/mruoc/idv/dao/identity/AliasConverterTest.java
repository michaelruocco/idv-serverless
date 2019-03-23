package uk.co.mruoc.idv.dao.identity;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.CreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.DebitCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasConverterTest {

    @Test
    public void shouldConvertIdvId() {
        final String value = "IDV_ID|CLEAR_TEXT|786fa43d-6bcd-4a0c-ab7e-21348eb77faf";

        final Alias alias = AliasConverter.toAlias(value);

        assertThat(alias).isEqualTo(new IdvIdAlias(UUID.fromString("786fa43d-6bcd-4a0c-ab7e-21348eb77faf")));
    }

    @Test
    public void shouldConvertCreditCardNumber() {
        final String value = "CREDIT_CARD_NUMBER|TOKENIZED|4929123456789012";

        final Alias alias = AliasConverter.toAlias(value);

        assertThat(alias).isEqualTo(new CreditCardNumberAlias(Alias.Formats.TOKENIZED, "4929123456789012"));
    }

    @Test
    public void shouldConvertDebitCardNumber() {
        final String value = "DEBIT_CARD_NUMBER|TOKENIZED|4929123456789012";

        final Alias alias = AliasConverter.toAlias(value);

        assertThat(alias).isEqualTo(new DebitCardNumberAlias(Alias.Formats.TOKENIZED, "4929123456789012"));
    }

    @Test
    public void shouldConvertUnknownAlias() {
        final String value = "UNKNOWN|CLEAR_TEXT|ABC123";

        final Alias alias = AliasConverter.toAlias(value);

        final AliasType type = new DefaultAliasType("UNKNOWN");
        assertThat(alias).isEqualTo(new DefaultAlias(type, Alias.Formats.CLEAR_TEXT, "ABC123"));
    }

    @Test
    public void shouldConvertMultipleStringsToAliases() {
        final Collection<String> aliasStrings = Arrays.asList(
                "IDV_ID|CLEAR_TEXT|786fa43d-6bcd-4a0c-ab7e-21348eb77faf",
                "CREDIT_CARD_NUMBER|TOKENIZED|1234567890123456"
        );

        final Collection<Alias> aliases = AliasConverter.toAliases(aliasStrings);

        assertThat(aliases).containsExactly(
                new IdvIdAlias(UUID.fromString("786fa43d-6bcd-4a0c-ab7e-21348eb77faf")),
                new TokenizedCreditCardNumberAlias("1234567890123456")
        );
    }

    @Test
    public void shouldConvertIdvIdToString() {
        final Alias alias = new IdvIdAlias(UUID.fromString("786fa43d-6bcd-4a0c-ab7e-21348eb77faf"));

        final String value = AliasConverter.toString(alias);

        assertThat(value).isEqualTo("IDV_ID|CLEAR_TEXT|786fa43d-6bcd-4a0c-ab7e-21348eb77faf");
    }

    @Test
    public void shouldConvertCreditCardNumberToString() {
        final Alias alias = new CreditCardNumberAlias(Alias.Formats.TOKENIZED, "4929123456789012");

        final String value = AliasConverter.toString(alias);

        assertThat(value).isEqualTo("CREDIT_CARD_NUMBER|TOKENIZED|4929123456789012");
    }

    @Test
    public void shouldConvertDebitCardNumberToString() {
        final Alias alias = new DebitCardNumberAlias(Alias.Formats.TOKENIZED, "4929123456789012");

        final String value = AliasConverter.toString(alias);

        assertThat(value).isEqualTo("DEBIT_CARD_NUMBER|TOKENIZED|4929123456789012");
    }

    @Test
    public void shouldConvertUnknownAliasToString() {
        final AliasType type = new DefaultAliasType("UNKNOWN");
        final Alias alias = new DefaultAlias(type, Alias.Formats.CLEAR_TEXT, "ABC123");

        final String value = AliasConverter.toString(alias);

        assertThat(value).isEqualTo("UNKNOWN|CLEAR_TEXT|ABC123");
    }

    @Test
    public void shouldConvertMultipleAliasesToStrings() {
        final Aliases aliases = Aliases.with(
                new IdvIdAlias(UUID.fromString("786fa43d-6bcd-4a0c-ab7e-21348eb77faf")),
                new TokenizedCreditCardNumberAlias("1234567890123456")
        );

        final Set<String> aliasStrings = AliasConverter.toStrings(aliases);

        assertThat(aliasStrings).containsExactlyInAnyOrder(
                "IDV_ID|CLEAR_TEXT|786fa43d-6bcd-4a0c-ab7e-21348eb77faf",
                "CREDIT_CARD_NUMBER|TOKENIZED|1234567890123456"
        );
    }

}
