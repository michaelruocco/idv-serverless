package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedDebitCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.dao.identity.FakeIdentityDao;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class GetIdentityHandlerIntegrationTest {

    private final IdentityDao dao = new FakeIdentityDao();
    private final GetIdentityHandler handler = new GetIdentityHandler(dao);

    private final IdvIdAlias idvIdAlias = new IdvIdAlias("3713f6f6-8fa6-4686-bcbc-e348ee3b4b06");
    private final Alias alias = new TokenizedCreditCardNumberAlias("1111111111111111");

    @Before
    public void setUp() {
        final Identity identity = Identity.withAliases(
                idvIdAlias,
                alias,
                new TokenizedDebitCardNumberAlias("2222222222222222")
        );
        dao.save(identity);
    }

    @Test
    public void shouldReturnIdentityById() {
        final Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("id", idvIdAlias.getValue());
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
        queryStringParameters.put("aliasType", alias.getTypeName());
        queryStringParameters.put("aliasFormat", alias.getFormat());
        queryStringParameters.put("aliasValue", alias.getValue());
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withQueryStringParameters(queryStringParameters);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, null);

        final String expectedBody = loadContentFromClasspath("/identity.json");
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualToIgnoringWhitespace(expectedBody);
    }

}
