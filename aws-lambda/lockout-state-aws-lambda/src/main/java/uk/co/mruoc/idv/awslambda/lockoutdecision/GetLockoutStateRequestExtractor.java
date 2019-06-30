package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.AliasExtractor;
import uk.co.mruoc.idv.core.lockoutstate.model.DefaultLockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;

import java.util.Map;

@Slf4j
public class GetLockoutStateRequestExtractor implements LockoutStateRequestExtractor {

    private final AliasExtractor aliasExtractor;

    public GetLockoutStateRequestExtractor(final AliasExtractor aliasExtractor) {
        this.aliasExtractor = aliasExtractor;
    }

    @Override
    public LockoutStateRequest extractRequest(final APIGatewayProxyRequestEvent input) {
        final Map<String, String> parameters = input.getQueryStringParameters();
        return DefaultLockoutStateRequest.builder()
                .alias(aliasExtractor.extractAlias(input))
                .activityType(parameters.get("activityType"))
                .channelId(parameters.get("channelId"))
                .build();
    }

}
