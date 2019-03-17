package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.jsonapi.identity.AliasDeserializer;
import uk.co.mruoc.idv.jsonapi.identity.IdentityDeserializer;
import uk.co.mruoc.idv.jsonapi.verificationcontext.activity.ActivityDeserializer;
import uk.co.mruoc.idv.jsonapi.verificationcontext.method.VerificationMethodDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
public class VerificationContextDeserializer extends StdDeserializer<VerificationContext> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public VerificationContextDeserializer() {
        super(VerificationContext.class);
    }

    @Override
    public VerificationContext deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode contextNode = parser.readValueAsTree();
        return VerificationContext.builder()
                .id(extractId(contextNode))
                .channel(extractChannel(contextNode))
                .inputAlias(extractInputAlias(contextNode))
                .identity(extractIdentity(contextNode))
                .activity(extractActivity(contextNode))
                .created(extractCreated(contextNode))
                .expiry(extractExpiry(contextNode))
                .eligibleMethods(toEligibleMethods(contextNode))
                .build();
    }

    private static UUID extractId(final JsonNode node) {
        return UUID.fromString(node.get("id").asText());
    }

    private static Channel extractChannel(final JsonNode contextNode) {
        final JsonNode channelNode = contextNode.get("channel");
        final String id = channelNode.get("id").asText();
        return new DefaultChannel(id);
    }

    private static Alias extractInputAlias(final JsonNode contextNode) {
        final JsonNode inputAliasNode = contextNode.get("inputAlias");
        return AliasDeserializer.toAlias(inputAliasNode);
    }

    private static Identity extractIdentity(final JsonNode contextNode) {
        final JsonNode identityNode = contextNode.get("identity");
        return IdentityDeserializer.toIdentity(identityNode);
    }

    private static Activity extractActivity(final JsonNode contextNode) {
        final JsonNode activityNode = contextNode.get("activity");
        final ActivityDeserializer deserializer = new ActivityDeserializer(MAPPER);
        return deserializer.toActivity(activityNode);
    }

    private static Instant extractCreated(final JsonNode contextNode) {
        return extractInstant("created", contextNode);
    }

    private static Instant extractExpiry(final JsonNode contextNode) {
        return extractInstant("expiry", contextNode);
    }

    private static Instant extractInstant(final String name, final JsonNode activityNode) {
        return Instant.parse(activityNode.get(name).asText());
    }

    private static Collection<VerificationMethodSequence> toEligibleMethods(final JsonNode contextNode) {
        final List<VerificationMethodSequence> eligibleMethods = new ArrayList<>();
        final JsonNode eligibleMethodsNode = contextNode.get("eligibleMethods");
        for (final JsonNode eligibleMethodNode : eligibleMethodsNode) {
            eligibleMethods.add(toEligibleMethod(eligibleMethodNode));
        }
        return Collections.unmodifiableCollection(eligibleMethods);
    }

    private static VerificationMethodSequence toEligibleMethod(final JsonNode eligibleMethodNode) {
        final List<VerificationMethod> methodSequence = new ArrayList<>();
        final String name = extractName(eligibleMethodNode);
        final JsonNode methodSequenceNode = eligibleMethodNode.get("sequence");
        for (final JsonNode methodNode : methodSequenceNode) {
            methodSequence.add(toMethod(methodNode));
        }
        return new VerificationMethodSequence(name, methodSequence);
    }

    private static VerificationMethod toMethod(final JsonNode methodNode) {
        final VerificationMethodDeserializer deserializer = new VerificationMethodDeserializer(MAPPER);
        return deserializer.toMethod(methodNode);
    }

    private static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }


}
