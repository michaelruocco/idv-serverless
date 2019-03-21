package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.BukCustomerIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.EncryptedCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.EncryptedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.EncryptedDebitCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedDebitCardNumberAlias;
import uk.co.mruoc.idv.json.identity.IdentityObjectMapperSingleton;

import java.io.IOException;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class IdentityJsonApiDocumentTest {

    private static final String IDENTITY_JSON = loadJson("/identity.json");
    private static final IdentityJsonApiDocument DOCUMENT = buildDocument();

    private static final ObjectMapper MAPPER = IdentityObjectMapperSingleton.get();

    @Test
    public void shouldSerializeDocument() throws JsonProcessingException {
        final IdentityJsonApiDocument identity = buildDocument();

        final String json = MAPPER.writeValueAsString(identity);

        assertThat(json).isEqualTo(IDENTITY_JSON);
    }

    @Test
    public void shouldDeserializeDocument() throws IOException {
        final IdentityJsonApiDocument document = MAPPER.readValue(IDENTITY_JSON, IdentityJsonApiDocument.class);

        assertThat(document).isEqualToComparingFieldByFieldRecursively(DOCUMENT);
    }

    @Test
    public void shouldThrowExceptionIfMandatoryFieldIsMissingFromJson() {
        final String invalidJson = loadJson("/identity-mandatory-field-missing.json");

        final Throwable thrown = catchThrowable(() -> MAPPER.readValue(invalidJson, IdentityJsonApiDocument.class));

        assertThat(thrown)
                .isInstanceOf(JsonMappingException.class)
                .hasCauseInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldUseDefaultAliasAndDefaultAliasTypeForUnknownAliasType() throws IOException {
        final String unknownAliasTypeJson = loadJson("/identity-unknown-alias-type.json");

        final IdentityJsonApiDocument document = MAPPER.readValue(unknownAliasTypeJson, IdentityJsonApiDocument.class);

        final Identity identity = document.getIdentity();
        assertThat(identity.getAliases()).containsExactlyInAnyOrder(
                new IdvIdAlias(UUID.fromString("23d106b4-0003-4ad8-8fc2-7f3a601c2125")),
                new DefaultAlias(new DefaultAliasType("UNKNOWN"), Alias.Formats.CLEAR_TEXT, "ABC123")
        );
    }

    private static String loadJson(final String path) {
        return deleteWhitespace(loadContentFromClasspath(path));
    }

    private static IdentityJsonApiDocument buildDocument() {
        final Identity identity = Identity.withAliases(
                new IdvIdAlias(UUID.fromString("21b4d9e0-11c3-4e84-aa87-dc37d7f59e23")),
                new BukCustomerIdAlias("11111111"),
                new UkcCardholderIdAlias("2222222222"),
                new TokenizedCreditCardNumberAlias("3489347343788005"),
                new EncryptedCreditCardNumberAlias("DAJKSDJASJKDASJcnmzcnsadas"),
                new TokenizedDebitCardNumberAlias("3489347343789008"),
                new EncryptedDebitCardNumberAlias("DAJKSDJASJKDASJcnmzcnsfrfr"),
                new TokenizedCardNumberAlias("3489347343788005"),
                new EncryptedCardNumberAlias("DAJKSDJASJKDASJcnmzcnsfrfr")
        );
        return new IdentityJsonApiDocument(identity);
    }

}
