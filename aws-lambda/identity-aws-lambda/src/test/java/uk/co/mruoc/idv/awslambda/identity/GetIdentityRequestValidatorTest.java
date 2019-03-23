package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.RequestValidator;
import uk.co.mruoc.idv.awslambda.identity.GetIdentityRequestValidator.IdentityRequestInvalidException;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class GetIdentityRequestValidatorTest {

    private static final String ALIAS_TYPE = AliasType.Names.CREDIT_CARD_NUMBER;

    private final RequestValidator validator = new GetIdentityRequestValidator();

    @Test
    public void shouldThrowExceptionIfIdPathParametersAndQueryStringParametersAreBothNull() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();

        final Throwable cause = catchThrowable(() -> validator.validate(request));

        assertThat(cause).isInstanceOf(IdentityRequestInvalidException.class);
    }

    @Test
    public void shouldThrowExceptionIfIdPathParameterIdAndAliasQueryStringParametersAreNotProvided() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(Collections.emptyMap())
                .withQueryStringParameters(Collections.emptyMap());

        final Throwable cause = catchThrowable(() -> validator.validate(request));

        assertThat(cause).isInstanceOf(IdentityRequestInvalidException.class);
    }

    @Test
    public void shouldThrowExceptionIfIdPathParameterIdAndAliasValueQueryStringParameterIsNotProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("aliasType", ALIAS_TYPE);
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(Collections.emptyMap())
                .withQueryStringParameters(Collections.unmodifiableMap(parameters));

        final Throwable cause = catchThrowable(() -> validator.validate(request));

        assertThat(cause).isInstanceOf(IdentityRequestInvalidException.class);
    }

    @Test
    public void shouldThrowExceptionIfIdPathParameterIdAndAliasTypeQueryStringParameterIsNotProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("aliasValue", "12345678");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(Collections.emptyMap())
                .withQueryStringParameters(Collections.unmodifiableMap(parameters));

        final Throwable cause = catchThrowable(() -> validator.validate(request));

        assertThat(cause).isInstanceOf(IdentityRequestInvalidException.class);
    }

    @Test
    public void shouldReturnTrueIfIdPathParameterIsProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("id", UUID.randomUUID().toString());
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(Collections.unmodifiableMap(parameters));

        final boolean valid = validator.validate(request);

        assertThat(valid).isTrue();
    }

    @Test
    public void shouldReturnTrueIfAliasQueryStringParametersAreProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("aliasType", ALIAS_TYPE);
        parameters.put("aliasValue", "12345678");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withQueryStringParameters(Collections.unmodifiableMap(parameters));

        final boolean valid = validator.validate(request);

        assertThat(valid).isTrue();
    }

}
