package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;

public interface LockoutStateRequestExtractor {

    LockoutStateRequest extractRequest(APIGatewayProxyRequestEvent input);

    class InvalidLockoutStateRequestException extends RuntimeException {

        public InvalidLockoutStateRequestException(final Throwable cause) {
            super(cause);
        }

    }

}
