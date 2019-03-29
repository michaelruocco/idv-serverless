package uk.co.mruoc.idv.awslambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Slf4j
public class ExceptionConverter {

    private final JsonConverter jsonConverter;
    private final ErrorHandlerDelegator errorHandler;

    public APIGatewayProxyResponseEvent toResponse(final Exception exception) {
        final JsonApiErrorDocument document = errorHandler.toDocument(exception);
        final APIGatewayProxyResponseEvent event = new APIGatewayProxyResponseEvent()
                .withBody(jsonConverter.toJson(document))
                .withStatusCode(calculateErrorStatusCode(document));
        log.info("returning error response {}", event);
        return event;
    }

    private static int calculateErrorStatusCode(final JsonApiErrorDocument document) {
        final Set<Integer> codes = document.getErrors().stream()
                .map(JsonApiErrorItem::getStatusCode)
                .collect(Collectors.toSet());
        return codes.iterator().next();
    }

}
