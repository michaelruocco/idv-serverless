package uk.co.mruoc.idv.json.authorizer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class AuthPolicy {

    private final String principalId;
    private final AuthPolicyDocument policyDocument;

    public String getVersion() {
        if (hasPolicyDocument()) {
            return policyDocument.getVersion();
        }
        return null;
    }

    public Collection<AuthPolicyStatement> getStatements() {
        if (hasPolicyDocument()) {
            return policyDocument.getStatements();
        }
        return null;
    }

    private boolean hasPolicyDocument() {
        return policyDocument != null;
    }

}
