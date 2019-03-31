package uk.co.mruoc.idv.awslambda.authorizer.model;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthPolicyDocumentTest {

    private static final String VERSION = "version";
    private static final List<AuthPolicyStatement> STATEMENTS = Collections.emptyList();

    private final AuthPolicyDocument document = new AuthPolicyDocument(VERSION, STATEMENTS);

    @Test
    public void shouldReturnVersion() {
        assertThat(document.getVersion()).isEqualTo(VERSION);
    }

    @Test
    public void shouldReturnStatements() {
        assertThat(document.getStatements()).isEqualTo(STATEMENTS);
    }

    @Test
    public void shouldHaveNoArgumentConstructorForJackson() {
        final AuthPolicyDocument noArgDocument = new AuthPolicyDocument();

        assertThat(noArgDocument.getVersion()).isNull();
        assertThat(noArgDocument.getStatements()).isNull();
    }

}
