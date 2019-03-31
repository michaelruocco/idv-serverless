package uk.co.mruoc.idv.awslambda.authorizer.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthPolicyResponseTest {

    private static final String PRINCIPAL_ID = "principalId";
    private static final Map<String, Object> POLICY_DOCUMENT = new HashMap<>();

    @Test
    public void shouldReturnConstructorArguments() {
        final AuthPolicyResponse context = new AuthPolicyResponse(PRINCIPAL_ID, POLICY_DOCUMENT);

        assertThat(context.getPrincipalId()).isEqualTo(PRINCIPAL_ID);
        assertThat(context.getPolicyDocument()).isEqualTo(POLICY_DOCUMENT);
    }

    @Test
    public void shouldDefaultAllValuesToNullIfNotProvided() {
        final AuthPolicyResponse context = new AuthPolicyResponse();

        assertThat(context.getPrincipalId()).isNull();
        assertThat(context.getPolicyDocument()).isNull();
    }

}
