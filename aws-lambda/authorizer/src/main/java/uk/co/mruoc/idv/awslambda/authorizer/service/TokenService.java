package uk.co.mruoc.idv.awslambda.authorizer.service;

public interface TokenService {
    String create(TokenRequest tokenRequest);

    DecodedToken decode(String token);
}
