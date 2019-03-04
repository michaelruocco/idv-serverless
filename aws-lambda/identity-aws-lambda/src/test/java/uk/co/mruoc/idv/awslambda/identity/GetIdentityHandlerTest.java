package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.identity.GetIdentityRequestValidator.IdentityIdOrAliasNotProvidedError;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class GetIdentityHandlerTest {

    private static final String IDENTITY_JSON = loadContentFromClasspath("/identity.json");
    private static final String BAD_REQUEST_JSON = loadContentFromClasspath("/get-identity-bad-request.json");

    private static final Alias IDV_ID_ALIAS = new IdvIdAlias("3713f6f6-8fa6-4686-bcbc-e348ee3b4b06");
    private static final Alias UKC_CARDHOLDER_ID_ALIAS = new UkcCardholderIdAlias("12345678");

    private final ObjectMapper mapper = ObjectMapperSingleton.get();
    private final IdentityService identityService = mock(IdentityService.class);
    private final RequestValidator requestValidator = mock(RequestValidator.class);

    private final Context context = mock(Context.class);

    private final GetIdentityHandler handler = new GetIdentityHandler(mapper, identityService, requestValidator);

    @Test
    public void shouldReturnErrorIfRequestIsInvalid() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        given(requestValidator.validate(request)).willReturn(Optional.of(new JsonApiErrorDocument(new IdentityIdOrAliasNotProvidedError())));

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getBody()).isEqualToIgnoringWhitespace(BAD_REQUEST_JSON);
    }

    @Test
    public void shouldLoadIdentityById() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(buildPathParameters(IDV_ID_ALIAS.getValue()));
        given(requestValidator.validate(request)).willReturn(Optional.empty());
        given(identityService.load(IDV_ID_ALIAS)).willReturn(Identity.withAliases(IDV_ID_ALIAS, UKC_CARDHOLDER_ID_ALIAS));

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualToIgnoringWhitespace(IDENTITY_JSON);
    }

    @Test
    public void shouldLoadIdentityByAlias() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withQueryStringParameters(buildQueryStringParameters(UKC_CARDHOLDER_ID_ALIAS));
        given(requestValidator.validate(request)).willReturn(Optional.empty());
        given(identityService.load(UKC_CARDHOLDER_ID_ALIAS)).willReturn(Identity.withAliases(IDV_ID_ALIAS, UKC_CARDHOLDER_ID_ALIAS));

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualToIgnoringWhitespace(IDENTITY_JSON);
    }

    private static Map<String, String> buildPathParameters(final String id) {
        final Map<String, String> params = new HashMap<>();
        params.put("id", id);
        return Collections.unmodifiableMap(params);
    }

    private static Map<String, String> buildQueryStringParameters(final Alias alias) {
        final Map<String, String> params = new HashMap<>();
        params.put("aliasType", alias.getTypeName());
        params.put("aliasValue", alias.getValue());
        return Collections.unmodifiableMap(params);
    }

}
