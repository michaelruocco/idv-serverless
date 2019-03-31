package uk.co.mruoc.idv.awslambda.authorizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AuthPolicyDocument {

    @JsonProperty("Version")
    private final String version;

    @JsonProperty("Statement")
    private final Collection<AuthPolicyStatement> statements;

}
