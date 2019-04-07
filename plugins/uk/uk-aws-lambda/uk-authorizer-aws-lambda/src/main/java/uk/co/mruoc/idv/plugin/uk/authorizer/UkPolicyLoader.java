package uk.co.mruoc.idv.plugin.uk.authorizer;

import uk.co.mruoc.idv.awslambda.authorizer.service.DefaultPolicyLoader;
import uk.co.mruoc.idv.awslambda.authorizer.service.PolicyTemplatePopulator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class UkPolicyLoader extends DefaultPolicyLoader {

    public UkPolicyLoader() {
        super(loadDefaultPolicy(),
                loadUserPolicies(),
                new PolicyTemplatePopulator());
    }

    private static String loadDefaultPolicy() {
        return loadContentFromClasspath("/deny-all-policy.json");
    }

    private static Map<String, String> loadUserPolicies() {
        final Map<String, String> userPolicies = new HashMap<>();
        userPolicies.put("idv-test-user", loadContentFromClasspath("/allow-all-policy.json"));
        return Collections.unmodifiableMap(userPolicies);
    }

}
