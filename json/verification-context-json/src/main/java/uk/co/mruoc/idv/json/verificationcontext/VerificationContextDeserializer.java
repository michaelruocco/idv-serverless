package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.channel.model.Channel;
import uk.co.mruoc.idv.core.channel.model.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.RegisterAttemptStrategy;
import uk.co.mruoc.idv.json.verificationcontext.activity.ActivityDeserializer;
import uk.co.mruoc.idv.json.verificationcontext.method.VerificationMethodDeserializer;
import uk.co.mruoc.idv.json.identity.AliasDeserializer;
import uk.co.mruoc.idv.json.identity.IdentityDeserializer;

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
                .providedAlias(extractProvidedAlias(contextNode))
                .identity(extractIdentity(contextNode))
                .activity(extractActivity(contextNode))
                .created(extractCreated(contextNode))
                .expiry(extractExpiry(contextNode))
                .sequences(toVerificationMethodSequences(contextNode))
                .build();
    }

    private static UUID extractId(final JsonNode node) {
        if (node.has("id")) {
            return UUID.fromString(node.get("id").asText());
        }
        return null;
    }

    private static Channel extractChannel(final JsonNode contextNode) {
        final JsonNode channelNode = contextNode.get("channel");
        final String id = channelNode.get("id").asText();
        return new DefaultChannel(id);
    }

    private static Alias extractProvidedAlias(final JsonNode contextNode) {
        final JsonNode inputAliasNode = contextNode.get("providedAlias");
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

    private static Collection<VerificationMethodSequence> toVerificationMethodSequences(final JsonNode contextNode) {
        final List<VerificationMethodSequence> methodSequences = new ArrayList<>();
        final JsonNode sequencesNode = contextNode.get("sequences");
        for (final JsonNode sequenceNode : sequencesNode) {
            methodSequences.add(toVerificationMethodSequence(sequenceNode));
        }
        return Collections.unmodifiableCollection(methodSequences);
    }

    private static VerificationMethodSequence toVerificationMethodSequence(final JsonNode sequenceNode) {
        final List<VerificationMethod> methods = new ArrayList<>();
        final String name = extractName(sequenceNode);
        final RegisterAttemptStrategy registerAttemptStrategy = extractRegisterAttemptStrategy(sequenceNode);
        final JsonNode methodsNode = sequenceNode.get("methods");
        for (final JsonNode methodNode : methodsNode) {
            methods.add(toMethod(methodNode));
        }
        return new VerificationMethodSequence(name, methods, registerAttemptStrategy);
    }

    private static VerificationMethod toMethod(final JsonNode methodNode) {
        final VerificationMethodDeserializer deserializer = new VerificationMethodDeserializer(MAPPER);
        return deserializer.toMethod(methodNode);
    }

    private static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }

    private static RegisterAttemptStrategy extractRegisterAttemptStrategy(final JsonNode node) {
        return RegisterAttemptStrategy.valueOf(node.get("registerAttemptStrategy").asText());
    }

}
