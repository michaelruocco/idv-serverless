package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextRequestDocument;

import java.io.IOException;
import java.io.UncheckedIOException;

@RequiredArgsConstructor
public class VerificationContextRequestExtractor {

    private final ObjectMapper mapper;

    public VerificationContextRequest extractRequest(final APIGatewayProxyRequestEvent input) {
        try {
            final VerificationContextRequestDocument document = mapper.readValue(input.getBody(), VerificationContextRequestDocument.class);
            return document.getRequest();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
