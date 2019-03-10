package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.RequestValidator;
import uk.co.mruoc.idv.awslambda.identity.GetIdentityRequestValidator.IdentityIdOrAliasNotProvidedError;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GetIdentityRequestValidatorTest {

    private static final String UKC_CARDHOLDER_ID_ALIAS_TYPE = AliasType.Names.UKC_CARDHOLDER_ID;

    private final RequestValidator validator = new GetIdentityRequestValidator();

    @Test
    public void shouldReturnErrorIfIdPathParametersAndQueryStringParametersAreBothNull() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();

        final Optional<JsonApiErrorDocument> document = validator.validate(request);

        assertThat(document.isPresent()).isTrue();
        assertThat(document.get()).isEqualToComparingFieldByFieldRecursively(new JsonApiErrorDocument(
                new IdentityIdOrAliasNotProvidedError())
        );
    }

    @Test
    public void shouldReturnErrorIfIdPathParameterIdAndAliasQueryStringParametersAreNotProvided() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(Collections.emptyMap())
                .withQueryStringParameters(Collections.emptyMap());

        final Optional<JsonApiErrorDocument> document = validator.validate(request);

        assertThat(document.isPresent()).isTrue();
        assertThat(document.get()).isEqualToComparingFieldByFieldRecursively(new JsonApiErrorDocument(
                new IdentityIdOrAliasNotProvidedError())
        );
    }

    @Test
    public void shouldReturnErrorIfIdPathParameterIdAndAliasValueQueryStringParameterIsNotProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("aliasType", UKC_CARDHOLDER_ID_ALIAS_TYPE);
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(Collections.emptyMap())
                .withQueryStringParameters(parameters);

        final Optional<JsonApiErrorDocument> document = validator.validate(request);

        assertThat(document.isPresent()).isTrue();
        assertThat(document.get()).isEqualToComparingFieldByFieldRecursively(new JsonApiErrorDocument(
                new IdentityIdOrAliasNotProvidedError())
        );
    }

    @Test
    public void shouldReturnErrorIfIdPathParameterIdAndAliasTypeQueryStringParameterIsNotProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("aliasValue", "12345678");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(Collections.emptyMap())
                .withQueryStringParameters(parameters);

        final Optional<JsonApiErrorDocument> document = validator.validate(request);

        assertThat(document.isPresent()).isTrue();
        assertThat(document.get()).isEqualToComparingFieldByFieldRecursively(new JsonApiErrorDocument(
                new IdentityIdOrAliasNotProvidedError())
        );
    }

    @Test
    public void shouldNotReturnErrorIfIdPathParameterIsProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("id", UUID.randomUUID().toString());
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParameters(parameters);

        final Optional<JsonApiErrorDocument> document = validator.validate(request);

        assertThat(document).isEmpty();
    }

    @Test
    public void shouldNotReturnErrorIfAliasQueryStringParametersAreProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("aliasType", UKC_CARDHOLDER_ID_ALIAS_TYPE);
        parameters.put("aliasValue", "12345678");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withQueryStringParameters(parameters);

        final Optional<JsonApiErrorDocument> document = validator.validate(request);

        assertThat(document).isEmpty();
    }

}
