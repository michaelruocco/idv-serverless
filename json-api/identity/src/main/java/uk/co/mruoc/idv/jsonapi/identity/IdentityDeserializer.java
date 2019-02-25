package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.model.alias.BukCustomerIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.SensitiveAliasFormat;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.EncryptedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.EncryptedDebitCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedDebitCardNumberAlias;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IdentityDeserializer extends StdDeserializer<Identity> {

    public IdentityDeserializer() {
        super(Identity.class);
    }

    @Override
    public Identity deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        final JsonNode identityNode = parser.readValueAsTree();
        final Aliases aliases = toAliases(identityNode.get("aliases"));
        return Identity.withAliases(aliases);
    }

    private static Aliases toAliases(final JsonNode aliasesNode) {
        final List<Alias> aliases = new ArrayList<>();
        for (int i = 0; i < aliasesNode.size(); i++) {
            aliases.add(toAlias(aliasesNode.get(i)));
        }
        return Aliases.with(aliases);
    }

    private static Alias toAlias(final JsonNode aliasNode) {
        final AliasType type = extractType(aliasNode);
        switch (type) {
            case IDV_ID:
                return toIdvId(aliasNode);
            case UKC_CARDHOLDER_ID:
                return toCardholderId(aliasNode);
            case BUK_CUSTOMER_ID:
                return toCustomerId(aliasNode);
            case CREDIT_CARD_NUMBER:
                return toCreditCardNumber(aliasNode);
            case DEBIT_CARD_NUMBER:
                return toDebitCardNumber(aliasNode);
            default:
                throw new InvalidAliasTypeException(type);
        }
    }

    private static Alias toIdvId(final JsonNode aliasNode) {
        final String value = extractValue(aliasNode);
        return new IdvIdAlias(UUID.fromString(value));
    }

    private static Alias toCardholderId(final JsonNode aliasNode) {
        final String value = extractValue(aliasNode);
        return new UkcCardholderIdAlias(value);
    }

    private static Alias toCustomerId(final JsonNode aliasNode) {
        final String value = extractValue(aliasNode);
        return new BukCustomerIdAlias(value);
    }

    private static Alias toCreditCardNumber(final JsonNode aliasNode) {
        final SensitiveAliasFormat format = extractFormat(aliasNode);
        final String value = extractValue(aliasNode);
        if (format == SensitiveAliasFormat.ENCRYPTED) {
            return new EncryptedCreditCardNumberAlias(value);
        }
        return new TokenizedCreditCardNumberAlias(value);
    }

    private static Alias toDebitCardNumber(final JsonNode aliasNode) {
        final SensitiveAliasFormat format = extractFormat(aliasNode);
        final String value = extractValue(aliasNode);
        if (format == SensitiveAliasFormat.ENCRYPTED) {
            return new EncryptedDebitCardNumberAlias(value);
        }
        return new TokenizedDebitCardNumberAlias(value);
    }

    private static AliasType extractType(final JsonNode aliasNode) {
        return AliasType.valueOf(aliasNode.get("type").asText());
    }

    private static SensitiveAliasFormat extractFormat(final JsonNode aliasNode) {
        return SensitiveAliasFormat.valueOf(aliasNode.get("format").asText());
    }

    private static String extractValue(final JsonNode aliasNode) {
        return aliasNode.get("value").asText();
    }

    public static class InvalidAliasTypeException extends RuntimeException {

        public InvalidAliasTypeException(final AliasType type) {
            super(type.name());
        }

    }

}
