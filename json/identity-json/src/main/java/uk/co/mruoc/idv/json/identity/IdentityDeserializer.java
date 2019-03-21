package uk.co.mruoc.idv.json.identity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class IdentityDeserializer extends StdDeserializer<Identity> {

    public IdentityDeserializer() {
        super(Identity.class);
    }

    @Override
    public Identity deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode identityNode = parser.readValueAsTree();
        return toIdentity(identityNode);
    }

    public static Identity toIdentity(final JsonNode identityNode) {
        final Aliases aliases = toAliases(identityNode.get("aliases"));
        return Identity.withAliases(aliases);
    }

    private static Aliases toAliases(final JsonNode aliasesNode) {
        final List<Alias> aliases = new ArrayList<>();
        for (int i = 0; i < aliasesNode.size(); i++) {
            aliases.add(AliasDeserializer.toAlias(aliasesNode.get(i)));
        }
        return Aliases.with(aliases);
    }

}
