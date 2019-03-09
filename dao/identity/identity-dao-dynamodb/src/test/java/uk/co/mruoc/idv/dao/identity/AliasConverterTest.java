package uk.co.mruoc.idv.dao.identity;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.model.alias.BukCustomerIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.CreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.DebitCardNumberAlias;

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
    public void shouldConvertCardholderId() {
        final String value = "UKC_CARDHOLDER_ID|CLEAR_TEXT|12345678";

        final Alias alias = AliasConverter.toAlias(value);

        assertThat(alias).isEqualTo(new UkcCardholderIdAlias("12345678"));
    }

    @Test
    public void shouldConvertCustomerId() {
        final String value = "BUK_CUSTOMER_ID|CLEAR_TEXT|1111111111";

        final Alias alias = AliasConverter.toAlias(value);

        assertThat(alias).isEqualTo(new BukCustomerIdAlias("1111111111"));
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
                "UKC_CARDHOLDER_ID|CLEAR_TEXT|12345678"
        );

        final Collection<Alias> aliases = AliasConverter.toAliases(aliasStrings);

        assertThat(aliases).containsExactly(
                new IdvIdAlias(UUID.fromString("786fa43d-6bcd-4a0c-ab7e-21348eb77faf")),
                new UkcCardholderIdAlias("12345678")
        );
    }

    @Test
    public void shouldConvertIdvIdToString() {
        final Alias alias = new IdvIdAlias(UUID.fromString("786fa43d-6bcd-4a0c-ab7e-21348eb77faf"));

        final String value = AliasConverter.toString(alias);

        assertThat(value).isEqualTo("IDV_ID|CLEAR_TEXT|786fa43d-6bcd-4a0c-ab7e-21348eb77faf");
    }

    @Test
    public void shouldConvertCardholderIdToString() {
        final Alias alias = new UkcCardholderIdAlias("12345678");

        final String value = AliasConverter.toString(alias);

        assertThat(value).isEqualTo("UKC_CARDHOLDER_ID|CLEAR_TEXT|12345678");
    }

    @Test
    public void shouldConvertCustomerIdToString() {
        final Alias alias = new BukCustomerIdAlias("1111111111");

        final String value = AliasConverter.toString(alias);

        assertThat(value).isEqualTo("BUK_CUSTOMER_ID|CLEAR_TEXT|1111111111");
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
                new UkcCardholderIdAlias("12345678")
        );

        final Set<String> aliasStrings = AliasConverter.toStrings(aliases);

        assertThat(aliasStrings).containsExactly(
                "IDV_ID|CLEAR_TEXT|786fa43d-6bcd-4a0c-ab7e-21348eb77faf",
                "UKC_CARDHOLDER_ID|CLEAR_TEXT|12345678"
        );
    }

}
