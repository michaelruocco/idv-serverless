package uk.co.mruoc.idv.json.verificationcontext.result;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;

import java.io.IOException;

@Slf4j
public class VerificationMethodResultsDeserializer extends StdDeserializer<VerificationMethodResults> {

    private final JsonNodeConverter converter;

    public VerificationMethodResultsDeserializer() {
        this(new JsonNodeConverter());
    }

    public VerificationMethodResultsDeserializer(final JsonNodeConverter converter) {
        super(VerificationMethodResults.class);
        this.converter = converter;
    }

    @Override
    public VerificationMethodResults deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.readValueAsTree();
        return converter.toVerificationMethodResults(node);
    }

}
