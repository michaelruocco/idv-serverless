package uk.co.mruoc.idv.awslambda.authorizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenAuthorizerRequest {

    private String type;
    private String authorizationToken;
    private String methodArn;

}
