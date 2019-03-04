package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Optional;

public interface RequestValidator {

    Optional<JsonApiErrorDocument> validate(final APIGatewayProxyRequestEvent input);
}
