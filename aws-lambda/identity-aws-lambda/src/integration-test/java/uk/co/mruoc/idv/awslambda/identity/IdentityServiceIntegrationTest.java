package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class IdentityServiceIntegrationTest {

    private final IdentityDao dao = new FakeIdentityDao();
    private final GetIdentityHandler handler = new GetIdentityHandler(dao);

    @Test
    public void shouldReturnIdentityById() {
        final Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("id", "3713f6f6-8fa6-4686-bcbc-e348ee3b4b06");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(pathParameters);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        final String expectedBody = loadContentFromClasspath("/identity.json");
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualToIgnoringWhitespace(expectedBody);
    }

    @Test
    public void shouldReturnIdentityByAlias() {
        final Map<String, String> queryStringParameters = new HashMap<>();
        queryStringParameters.put("aliasType", "UKC_CARDHOLDER_ID");
        queryStringParameters.put("aliasValue", "12345678");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withQueryStringParameters(queryStringParameters);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        final String expectedBody = loadContentFromClasspath("/identity.json");
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualToIgnoringWhitespace(expectedBody);
    }

}
