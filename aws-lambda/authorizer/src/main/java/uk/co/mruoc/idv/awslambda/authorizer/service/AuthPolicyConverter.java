package uk.co.mruoc.idv.awslambda.authorizer.service;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.authorizer.model.AuthPolicy;
import uk.co.mruoc.idv.awslambda.authorizer.model.AuthPolicyResponse;
import uk.co.mruoc.idv.awslambda.authorizer.model.AuthPolicyStatement;
import uk.co.mruoc.idv.json.JsonConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class AuthPolicyConverter {

    private final JsonConverter jsonConverter;

    public AuthPolicyConverter(final JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    public AuthPolicyResponse toAuthPolicyResponse(final String policyJson) {
        log.info("converting policy json {} to auth policy response", policyJson);
        final AuthPolicy authPolicy = jsonConverter.toObject(policyJson, AuthPolicy.class);
        final AuthPolicyResponse response = toAuthPolicyResponse(authPolicy);
        log.info("converted to auth policy response {}", response);
        return response;
    }

    public AuthPolicyResponse toAuthPolicyResponse(final AuthPolicy authPolicy) {
        final Map<String, Object> serializablePolicy = new HashMap<>();
        serializablePolicy.put("Version", authPolicy.getVersion());
        serializablePolicy.put("Statement", toSerializableStatements(authPolicy.getStatements()));
        return new AuthPolicyResponse(authPolicy.getPrincipalId(), serializablePolicy);
    }

    private static Collection<Map<String, Object>> toSerializableStatements(final Collection<AuthPolicyStatement> statements) {
        return statements.stream().map(AuthPolicyConverter::toSerializableStatement).collect(Collectors.toList());
    }

    private static Map<String, Object> toSerializableStatement(final AuthPolicyStatement statement) {
        final Map<String, Object> serializableStatement = new HashMap<>();
        serializableStatement.put("Effect", statement.getEffect());
        serializableStatement.put("Action", statement.getAction());
        serializableStatement.put("Resource", statement.getResources());
        return Collections.unmodifiableMap(serializableStatement);
    }

}
