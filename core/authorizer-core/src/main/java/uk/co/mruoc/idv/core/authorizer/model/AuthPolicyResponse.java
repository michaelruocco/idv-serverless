package uk.co.mruoc.idv.core.authorizer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AuthPolicyResponse {

    private final String principalId;
    private final Map<String, Object> policyDocument;

}
