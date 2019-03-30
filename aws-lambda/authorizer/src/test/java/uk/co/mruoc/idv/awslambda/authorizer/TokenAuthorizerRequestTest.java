package uk.co.mruoc.idv.awslambda.authorizer;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.authorizer.model.TokenAuthorizerRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenAuthorizerRequestTest {

    private static final String TYPE = "TYPE";
    private static final String AUTHORIZATION_TOKEN = "authorizationToken";
    private static final String METHOD_ARN = "methodArn";

    @Test
    public void shouldReturnConstructorArguments() {
        final TokenAuthorizerRequest context = new TokenAuthorizerRequest(TYPE, AUTHORIZATION_TOKEN, METHOD_ARN);

        assertThat(context.getType()).isEqualTo(TYPE);
        assertThat(context.getAuthorizationToken()).isEqualTo(AUTHORIZATION_TOKEN);
        assertThat(context.getMethodArn()).isEqualTo(METHOD_ARN);
    }

    @Test
    public void shouldDefaultAllValuesToNullIfNotProvided() {
        final TokenAuthorizerRequest context = new TokenAuthorizerRequest();

        assertThat(context.getType()).isNull();
        assertThat(context.getAuthorizationToken()).isNull();
        assertThat(context.getMethodArn()).isNull();
    }

    @Test
    public void shouldSetType() {
        final TokenAuthorizerRequest context = new TokenAuthorizerRequest();

        context.setType(TYPE);

        assertThat(context.getType()).isEqualTo(TYPE);
    }

    @Test
    public void shouldSetAuthorizationToken() {
        final TokenAuthorizerRequest context = new TokenAuthorizerRequest();

        context.setAuthorizationToken(AUTHORIZATION_TOKEN);

        assertThat(context.getAuthorizationToken()).isEqualTo(AUTHORIZATION_TOKEN);
    }

    @Test
    public void shouldSetMethodArn() {
        final TokenAuthorizerRequest context = new TokenAuthorizerRequest();

        context.setMethodArn(METHOD_ARN);

        assertThat(context.getMethodArn()).isEqualTo(METHOD_ARN);
    }

}
