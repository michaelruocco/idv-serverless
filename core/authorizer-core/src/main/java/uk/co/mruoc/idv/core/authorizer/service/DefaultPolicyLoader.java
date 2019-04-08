package uk.co.mruoc.idv.core.authorizer.service;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.authorizer.model.PolicyRequest;

import java.util.Map;

@Slf4j
public class DefaultPolicyLoader implements PolicyLoader {

    private final String defaultPolicy;
    private final Map<String, String> policies;
    private final PolicyTemplatePopulator templatePopulator;

    public DefaultPolicyLoader(final String defaultPolicy, final Map<String, String> policies, final PolicyTemplatePopulator templatePopulator) {
        this.defaultPolicy = defaultPolicy;
        this.policies = policies;
        this.templatePopulator = templatePopulator;
    }

    @Override
    public String load(final PolicyRequest request) {
        log.info("loading policy for request", request);
        String template = loadTemplate(request.getPrincipalId());
        log.info("loaded policy template {}", template);
        String policy = apply(template, request);
        log.info("returning populated policy {}", policy);
        return policy;
    }

    private String loadTemplate(final String principalId) {
        if (policies.containsKey(principalId)) {
            final String specificPolicy = policies.get(principalId);
            log.info("loading specific policy template for principalId {} using {}", principalId, specificPolicy);
            return specificPolicy;
        }
        log.info("specific policy template not found for principalId {} using default {}", principalId, defaultPolicy);
        return defaultPolicy;
    }

    private String apply(final String template, final PolicyRequest request) {
        return templatePopulator.populate(template, request);
    }

}
