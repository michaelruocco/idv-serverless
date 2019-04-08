package uk.co.mruoc.idv.core.authorizer.service.jwt;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import uk.co.mruoc.idv.core.authorizer.model.DecodedToken;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Builder
public class ClaimsDecodedToken implements DecodedToken {

    private final String token;
    private final Claims claims;

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getId() {
        return claims.getId();
    }

    @Override
    public String getIssuer() {
        return claims.getIssuer();
    }

    @Override
    public String getSubject() {
        return claims.getSubject();
    }

    @Override
    public String getAudience() {
        return claims.getAudience();
    }

    @Override
    public Instant getIssuedAt() {
        return claims.getIssuedAt().toInstant();
    }

    @Override
    public Optional<Instant> getExpiration() {
        final Date expiration = claims.getExpiration();
        if (expiration == null) {
            return Optional.empty();
        }
        return Optional.of(expiration.toInstant());
    }

}
