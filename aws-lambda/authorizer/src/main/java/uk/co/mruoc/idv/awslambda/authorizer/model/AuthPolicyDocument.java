package uk.co.mruoc.idv.awslambda.authorizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class AuthPolicyDocument {

    @JsonProperty("Version")
    private final String version;

    @JsonProperty("Statement")
    private final List<AuthPolicyStatement> statements;

}
