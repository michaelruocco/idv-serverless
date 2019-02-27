package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAliasType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

public class GetIdentityHandlerTest {

    private static final String IDENTITY_JSON = deleteWhitespace(loadContentFromClasspath("/identity.json"));
    private static final String IDENTITY_ID = "3713f6f6-8fa6-4686-bcbc-e348ee3b4b06";
    private static final String UKC_CARDHOLDER_ID = "12345678";

    private final Context context = mock(Context.class);

    private final GetIdentityHandler handler = new GetIdentityHandler();

    @Test
    public void shouldLoadIdentityById() {
        final Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("id", IDENTITY_ID);
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(IDENTITY_JSON);
    }

    @Test
    public void shouldLoadIdentityByAlias() {
        final Map<String, String> queryStringParameters = new HashMap<>();
        queryStringParameters.put("aliasType", UkcCardholderIdAliasType.NAME);
        queryStringParameters.put("aliasValue", UKC_CARDHOLDER_ID);
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withQueryStringParameters(queryStringParameters);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(IDENTITY_JSON);
    }

}
