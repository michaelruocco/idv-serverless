package uk.co.mruoc.idv.awslambda.authorizer.service;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.authorizer.model.PolicyRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DefaultPolicyLoaderTest {

    private static final String DEFAULT_POLICY = "defaultPolicy";

    private final PolicyRequest request = PolicyRequest.builder()
            .principalId("principalId")
            .build();

    private final PolicyTemplatePopulator templatePopulator = mock(PolicyTemplatePopulator.class);

    @Test
    public void shouldUseDefaultPolicyIfNoSpecificPolicyConfigured() {
        final PolicyLoader loader = new DefaultPolicyLoader(DEFAULT_POLICY, Collections.emptyMap(), templatePopulator);
        final String expectedPolicy = "expectedPolicy";
        given(templatePopulator.populate(DEFAULT_POLICY, request)).willReturn(expectedPolicy);

        final String policy = loader.load(request);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    @Test
    public void shouldUseSpecificPolicyIfSpecificPolicyConfigured() {
        final Map<String, String> policies = new HashMap<>();
        final String specificPolicy = "specificPolicy";
        policies.put(request.getPrincipalId(), specificPolicy);
        final PolicyLoader loader = new DefaultPolicyLoader(DEFAULT_POLICY, Collections.unmodifiableMap(policies), templatePopulator);
        final String expectedPolicy = "expectedPolicy";
        given(templatePopulator.populate(specificPolicy, request)).willReturn(expectedPolicy);

        final String policy = loader.load(request);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

}
