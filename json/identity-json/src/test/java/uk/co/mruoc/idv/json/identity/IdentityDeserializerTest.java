package uk.co.mruoc.idv.json.identity;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.EncryptedCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.EncryptedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.EncryptedDebitCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedDebitCardNumberAlias;
import uk.co.mruoc.idv.json.JsonConverter;

import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class IdentityDeserializerTest {

    private static final String IDENTITY_JSON = loadContentFromClasspath("/identity.json");
    private static final Identity IDENTITY = buildIdentity();

    private final JsonConverter converter = new IdentityJsonConverterFactory().build();

    @Test
    public void shouldSerialize() {
        final String json = converter.toJson(IDENTITY);

        assertThatJson(json).isEqualTo(IDENTITY_JSON);
    }

    @Test
    public void shouldDeserialize() {
        final Identity identity = converter.toObject(IDENTITY_JSON, Identity.class);

        assertThat(identity).isEqualToComparingFieldByFieldRecursively(IDENTITY);
    }

    @Test
    public void shouldUseDefaultAliasAndDefaultAliasTypeForUnknownAliasType() {
        final String unknownAliasTypeJson = loadContentFromClasspath("/identity-unknown-alias-type.json");

        final Identity identity = converter.toObject(unknownAliasTypeJson, Identity.class);

        assertThat(identity.getAliases()).containsExactlyInAnyOrder(
                new IdvIdAlias(UUID.fromString("23d106b4-0003-4ad8-8fc2-7f3a601c2125")),
                new DefaultAlias(new DefaultAliasType("UNKNOWN"), Alias.Formats.CLEAR_TEXT, "ABC123")
        );
    }

    private static Identity buildIdentity() {
        return Identity.withAliases(
                new IdvIdAlias(UUID.fromString("21b4d9e0-11c3-4e84-aa87-dc37d7f59e23")),
                new TokenizedCreditCardNumberAlias("3489347343788005"),
                new EncryptedCreditCardNumberAlias("DAJKSDJASJKDASJcnmzcnsadas"),
                new TokenizedDebitCardNumberAlias("3489347343789008"),
                new EncryptedDebitCardNumberAlias("DAJKSDJASJKDASJcnmzcnsfrfr"),
                new TokenizedCardNumberAlias("3489347343788005"),
                new EncryptedCardNumberAlias("DAJKSDJASJKDASJcnmzcnsfrfr")
        );
    }

}
