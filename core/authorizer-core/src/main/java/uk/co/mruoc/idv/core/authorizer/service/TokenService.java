package uk.co.mruoc.idv.core.authorizer.service;

import uk.co.mruoc.idv.core.authorizer.model.DecodedToken;
import uk.co.mruoc.idv.core.authorizer.model.TokenRequest;
import uk.co.mruoc.idv.core.authorizer.model.TokenResponse;

public interface TokenService {

    TokenResponse create(final TokenRequest tokenRequest);

    DecodedToken decode(final String token);

    class TokenExpiredException extends RuntimeException {

        public TokenExpiredException(final Throwable cause) {
            super(cause);
        }

    }

}
