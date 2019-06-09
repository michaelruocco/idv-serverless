package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.AliasExtractor;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.RequestValidator;
import uk.co.mruoc.idv.awslambda.identity.GetIdentityRequestValidator.IdentityRequestInvalidException;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class GetIdentityHandlerTest {

    private static final Alias IDV_ID_ALIAS = new IdvIdAlias("3713f6f6-8fa6-4686-bcbc-e348ee3b4b06");
    private static final Alias CREDIT_CARD_NUMBER_ALIAS = new TokenizedCreditCardNumberAlias("1234567890123456");

    private final IdentityService identityService = mock(IdentityService.class);
    private final RequestValidator requestValidator = mock(RequestValidator.class);
    private final AliasExtractor aliasExtractor = mock(AliasExtractor.class);
    private final IdentityConverter identityConverter = mock(IdentityConverter.class);
    private final ExceptionConverter exceptionConverter = mock(ExceptionConverter.class);

    private final Context context = mock(Context.class);

    private final GetIdentityHandler handler = GetIdentityHandler.builder()
            .identityService(identityService)
            .requestValidator(requestValidator)
            .aliasExtractor(aliasExtractor)
            .identityConverter(identityConverter)
            .exceptionConverter(exceptionConverter)
            .build();

    @Test
    public void shouldReturnErrorIfExceptionIsThrown() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        final Exception exception = new IdentityRequestInvalidException();
        doThrow(exception).when(requestValidator).validate(request);

        final APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        given(exceptionConverter.toResponse(exception)).willReturn(expectedResponse);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void shouldLoadIdentity() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        given(aliasExtractor.extractAlias(request)).willReturn(IDV_ID_ALIAS);
        final Identity identity = Identity.withAliases(IDV_ID_ALIAS, CREDIT_CARD_NUMBER_ALIAS);
        given(identityService.load(IDV_ID_ALIAS)).willReturn(identity);

        final APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        given(identityConverter.toResponseEvent(identity)).willReturn(expectedResponse);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, context);

        assertThat(response).isEqualTo(expectedResponse);
    }

}
