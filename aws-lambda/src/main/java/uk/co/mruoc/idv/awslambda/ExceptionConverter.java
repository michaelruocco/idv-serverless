package uk.co.mruoc.idv.awslambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.io.UncheckedIOException;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Slf4j
public class ExceptionConverter {

    private final ObjectMapper mapper;
    private final ErrorHandlerDelegator errorHandler;

    public APIGatewayProxyResponseEvent toResponse(final Exception exception) {
        try {
            final JsonApiErrorDocument document = errorHandler.toDocument(exception);
            final APIGatewayProxyResponseEvent event = new APIGatewayProxyResponseEvent()
                    .withBody(mapper.writeValueAsString(document))
                    .withStatusCode(calculateErrorStatusCode(document));
            log.info("returning error response {}", event);
            return event;
        } catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static int calculateErrorStatusCode(final JsonApiErrorDocument document) {
        final Set<Integer> codes = document.getErrors().stream()
                .map(JsonApiErrorItem::getStatusCode)
                .collect(Collectors.toSet());
        return codes.iterator().next();
    }

}
