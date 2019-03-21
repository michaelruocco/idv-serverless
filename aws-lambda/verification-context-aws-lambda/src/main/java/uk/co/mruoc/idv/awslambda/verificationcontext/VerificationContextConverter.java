package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextResponseDocument;

import java.io.UncheckedIOException;

@RequiredArgsConstructor
public class VerificationContextConverter {

    private final ObjectMapper mapper;

    public VerificationContextResponseDocument toResponseDocument(final VerificationContext context) {
        return new VerificationContextResponseDocument(context);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final VerificationContext context) {
        final VerificationContextResponseDocument document = toResponseDocument(context);
        return toResponseEvent(document);
    }

    public APIGatewayProxyResponseEvent toResponseEvent(final VerificationContextResponseDocument document) {
        try {
            return new APIGatewayProxyResponseEvent()
                    .withBody(mapper.writeValueAsString(document))
                    .withStatusCode(201);
        } catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

}
