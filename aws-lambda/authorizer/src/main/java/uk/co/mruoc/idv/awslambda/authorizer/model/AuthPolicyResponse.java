package uk.co.mruoc.idv.awslambda.authorizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthPolicyResponse {

    private String principalId;
    private Map<String, Object> policyDocument;

}
