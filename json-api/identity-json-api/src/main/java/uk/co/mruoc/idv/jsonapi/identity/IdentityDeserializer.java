package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class IdentityDeserializer extends StdDeserializer<Identity> {

    public IdentityDeserializer() {
        super(Identity.class);
    }

    @Override
    public Identity deserialize(JsonParser parser, DeserializationContext context) throws IOException {
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
        log.debug("converting alias node {} to an alias", aliasNode);
        final String type = extractType(aliasNode);
        final String format = extractFormat(aliasNode);
        final String value = extractValue(aliasNode);
        return new DefaultAlias(new DefaultAliasType(type), format, value);
    }

    private static String extractType(final JsonNode aliasNode) {
        return aliasNode.get("type").asText();
    }

    private static String extractFormat(final JsonNode aliasNode) {
        return aliasNode.get("format").asText();
    }

    private static String extractValue(final JsonNode aliasNode) {
        return aliasNode.get("value").asText();
    }

}
