package uk.co.mruoc.idv.awslambda.authorizer.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthPolicyStatementTest {

    private static final String ACTION = "action";
    private static final List<String> RESOURCES = Arrays.asList("resource1", "resource2");
    private static final String EFFECT = "effect";

    private final AuthPolicyStatement statement = new AuthPolicyStatement(ACTION, RESOURCES, EFFECT);

    @Test
    public void shouldReturnAction() {
        assertThat(statement.getAction()).isEqualTo(ACTION);
    }

    @Test
    public void shouldReturnResources() {
        assertThat(statement.getResources()).isEqualTo(RESOURCES);
    }

    @Test
    public void shouldReturnEffect() {
        assertThat(statement.getEffect()).isEqualTo(EFFECT);
    }

    @Test
    public void shouldHaveNoArgumentConstructorForJackson() {
        final AuthPolicyStatement noArgStatement = new AuthPolicyStatement();

        assertThat(noArgStatement.getAction()).isNull();
        assertThat(noArgStatement.getResources()).isNull();
        assertThat(noArgStatement.getEffect()).isNull();
    }

}
