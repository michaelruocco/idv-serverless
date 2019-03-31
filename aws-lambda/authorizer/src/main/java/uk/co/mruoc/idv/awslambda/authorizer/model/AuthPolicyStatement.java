package uk.co.mruoc.idv.awslambda.authorizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class AuthPolicyStatement {

    @JsonProperty("Action")
    private final String action;

    @JsonProperty("Resource")
    private final List<String> resources;

    @JsonProperty("Effect")
    private final String effect;

}
