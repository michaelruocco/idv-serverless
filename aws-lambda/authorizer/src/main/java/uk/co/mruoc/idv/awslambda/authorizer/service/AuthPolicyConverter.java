package uk.co.mruoc.idv.awslambda.authorizer.service;

import uk.co.mruoc.idv.awslambda.authorizer.model.AuthPolicy;
import uk.co.mruoc.idv.awslambda.authorizer.model.AuthPolicyResponse;
import uk.co.mruoc.idv.awslambda.authorizer.model.AuthPolicyStatement;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthPolicyConverter {

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
