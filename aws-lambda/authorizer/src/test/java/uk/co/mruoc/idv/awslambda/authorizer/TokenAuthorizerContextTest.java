package uk.co.mruoc.idv.awslambda.authorizer;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.authorizer.model.TokenAuthorizerContext;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenAuthorizerContextTest {

    private static final String TYPE = "TYPE";
    private static final String AUTHORIZATION_TOKEN = "authorizationToken";
    private static final String METHOD_ARN = "methodArn";

    @Test
    public void shouldReturnConstructorArguments() {
        final TokenAuthorizerContext context = new TokenAuthorizerContext(TYPE, AUTHORIZATION_TOKEN, METHOD_ARN);

        assertThat(context.getType()).isEqualTo(TYPE);
        assertThat(context.getAuthorizationToken()).isEqualTo(AUTHORIZATION_TOKEN);
        assertThat(context.getMethodArn()).isEqualTo(METHOD_ARN);
    }

    @Test
    public void shouldDefaultAllValuesToNullIfNotProvided() {
        final TokenAuthorizerContext context = new TokenAuthorizerContext();

        assertThat(context.getType()).isNull();
        assertThat(context.getAuthorizationToken()).isNull();
        assertThat(context.getMethodArn()).isNull();
    }

    @Test
    public void shouldSetType() {
        final TokenAuthorizerContext context = new TokenAuthorizerContext();

        context.setType(TYPE);

        assertThat(context.getType()).isEqualTo(TYPE);
    }

    @Test
    public void shouldSetAuthorizationToken() {
        final TokenAuthorizerContext context = new TokenAuthorizerContext();

        context.setAuthorizationToken(AUTHORIZATION_TOKEN);

        assertThat(context.getAuthorizationToken()).isEqualTo(AUTHORIZATION_TOKEN);
    }

    @Test
    public void shouldSetMethodArn() {
        final TokenAuthorizerContext context = new TokenAuthorizerContext();

        context.setMethodArn(METHOD_ARN);

        assertThat(context.getMethodArn()).isEqualTo(METHOD_ARN);
    }

}
