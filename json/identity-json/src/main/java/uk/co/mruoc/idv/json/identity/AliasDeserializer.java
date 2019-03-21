package uk.co.mruoc.idv.json.identity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;

import java.io.IOException;

@Slf4j
public class AliasDeserializer extends StdDeserializer<Alias> {

    public AliasDeserializer() {
        super(Alias.class);
    }

    @Override
    public Alias deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode aliasNode = parser.readValueAsTree();
        return toAlias(aliasNode);
    }

    public static Alias toAlias(final JsonNode aliasNode) {
        log.debug("converting alias node {} to an alias", aliasNode);
        final String type = extractType(aliasNode);
        final String format = extractFormat(aliasNode);
        final String value = extractValue(aliasNode);
        final AliasType aliasType = AliasType.toAliasType(type);
        return new DefaultAlias(aliasType, format, value);
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
