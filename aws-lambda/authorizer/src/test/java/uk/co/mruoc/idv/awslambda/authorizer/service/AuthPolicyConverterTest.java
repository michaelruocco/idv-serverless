package uk.co.mruoc.idv.awslambda.authorizer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.authorizer.model.AuthPolicy;
import uk.co.mruoc.idv.awslambda.authorizer.model.AuthPolicyResponse;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class AuthPolicyConverterTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final AuthPolicyConverter converter = new AuthPolicyConverter();

    @Test
    public void shouldConvertAuthPolicyToAuthPolicyResponse() {
        final AuthPolicy policy = buildAuthPolicy();

        final AuthPolicyResponse response = converter.toAuthPolicyResponse(policy);

        assertThat(response.getPrincipalId()).isEqualTo("testPrincipalId");
        assertThat(response.getPolicyDocument()).contains(
                entry("Statement", buildExpectedStatements()),
                entry("Version", "2012-10-17")
        );
    }

    private static AuthPolicy buildAuthPolicy() {
        try {
            final String json = loadContentFromClasspath("/test-policy.json");
            return MAPPER.readValue(json, AuthPolicy.class);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Collection<Map<String, Object>> buildExpectedStatements() {
        final Map<String, Object> expectedStatement = new HashMap<>();
        expectedStatement.put("Action", "execute-api:Invoke");
        expectedStatement.put("Effect", "Allow");
        expectedStatement.put("Resource", Collections.singletonList("arn:aws:execute-api:test-region%:123456789:abc123/dev/*/*"));
        return Collections.singletonList(Collections.unmodifiableMap(expectedStatement));
    }

}
