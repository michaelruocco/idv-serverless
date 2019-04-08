package uk.co.mruoc.idv.json.authorizer;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class AuthPolicyTest {

    private static final String PRINCIPAL_ID = "principalId";
    private static final AuthPolicyDocument DOCUMENT = mock(AuthPolicyDocument.class);

    private final AuthPolicy policy = new AuthPolicy(PRINCIPAL_ID, DOCUMENT);

    @Test
    public void shouldReturnPrincipalId() {
        assertThat(policy.getPrincipalId()).isEqualTo(PRINCIPAL_ID);
    }

    @Test
    public void shouldReturnPolicyDocument() {
        assertThat(policy.getPolicyDocument()).isEqualTo(DOCUMENT);
    }

    @Test
    public void shouldReturnVersion() {
        final String expectedVersion = "version";
        given(DOCUMENT.getVersion()).willReturn(expectedVersion);

        final String version = policy.getVersion();

        assertThat(version).isEqualTo(expectedVersion);
    }

    @Test
    public void shouldReturnStatements() {
        final Collection<AuthPolicyStatement> expectedStatements = Collections.emptyList();
        given(DOCUMENT.getStatements()).willReturn(expectedStatements);

        final Collection<AuthPolicyStatement> statements = policy.getStatements();

        assertThat(statements).isEqualTo(expectedStatements);
    }

    @Test
    public void shouldHaveNoArgumentConstructorForJackson() {
        final AuthPolicy noArgPolicy = new AuthPolicy();

        assertThat(noArgPolicy.getPrincipalId()).isNull();
        assertThat(noArgPolicy.getVersion()).isNull();
        assertThat(noArgPolicy.getStatements()).isNull();
    }

}
