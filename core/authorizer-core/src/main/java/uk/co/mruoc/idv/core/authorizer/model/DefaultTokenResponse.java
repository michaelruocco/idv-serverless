package uk.co.mruoc.idv.core.authorizer.model;

import lombok.Builder;

@Builder
public class DefaultTokenResponse implements TokenResponse {

    private final String token;

    @Override
    public String getToken() {
        return token;
    }

}
