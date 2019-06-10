package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.AliasExtractor;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class GetLockoutStateRequestExtractorTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String ACTIVITY_TYPE = "ACTIVITY_TYPE";

    private final AliasExtractor aliasExtractor = mock(AliasExtractor.class);

    private final LockoutStateRequestExtractor extractor = new GetLockoutStateRequestExtractor(aliasExtractor);

    @Test
    public void shouldReturnLockoutStateRequestFromRequestEvent() {
        final APIGatewayProxyRequestEvent event = buildEvent();
        final Alias alias = new IdvIdAlias();
        given(aliasExtractor.extractAlias(event)).willReturn(alias);

        final LockoutStateRequest request = extractor.extractRequest(event);

        assertThat(request.getAlias()).isEqualTo(alias);
        assertThat(request.getAliasTypeName()).isEqualTo(alias.getTypeName());
        assertThat(request.getChannelId()).isEqualTo(CHANNEL_ID);
        assertThat(request.getActivityType()).isEqualTo(ACTIVITY_TYPE);
    }

    private APIGatewayProxyRequestEvent buildEvent() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("channelId", CHANNEL_ID);
        parameters.put("activityType", ACTIVITY_TYPE);
        return new APIGatewayProxyRequestEvent().withQueryStringParameters(Collections.unmodifiableMap(parameters));
    }

}
