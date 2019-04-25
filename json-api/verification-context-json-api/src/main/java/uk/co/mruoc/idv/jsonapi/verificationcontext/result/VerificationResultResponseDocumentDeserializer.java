package uk.co.mruoc.idv.jsonapi.verificationcontext.result;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.json.verificationcontext.result.JsonNodeConverter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class VerificationResultResponseDocumentDeserializer extends StdDeserializer<VerificationResultResponseDocument> {

    private final JsonNodeConverter converter;

    public VerificationResultResponseDocumentDeserializer() {
        this(new JsonNodeConverter());
    }

    public VerificationResultResponseDocumentDeserializer(final JsonNodeConverter converter) {
        super(VerificationResultResponseDocument.class);
        this.converter = converter;
    }

    @Override
    public VerificationResultResponseDocument deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.readValueAsTree();
        final JsonNode dataNode = node.get("data");
        final UUID id = extractId(dataNode);
        final JsonNode attributesNode = dataNode.get("attributes");
        final VerificationMethodResults results = converter.toVerificationMethodResults(attributesNode, id);
        return new VerificationResultResponseDocument(results);
    }

    private static UUID extractId(final JsonNode node) {
        return UUID.fromString(node.get("id").asText());
    }

}
