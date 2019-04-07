package uk.co.mruoc.idv.awslambda.authorizer.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.authorizer.service.DecodedToken;
import uk.co.mruoc.idv.awslambda.authorizer.service.KeyProvider;
import uk.co.mruoc.idv.awslambda.authorizer.service.TokenExpiredException;
import uk.co.mruoc.idv.awslambda.authorizer.service.TokenRequest;
import uk.co.mruoc.idv.awslambda.authorizer.service.TokenService;
import uk.co.mruoc.idv.core.service.TimeService;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenService implements TokenService {

    private final String issuer;
    private final KeyProvider keyProvider;
    private final TimeService timeService;

    @Override
    public String create(final TokenRequest tokenRequest) {
        final JwtBuilder builder = createJwtBuilder(tokenRequest);
        return buildAndLogToken(builder);
    }

    @Override
    public DecodedToken decode(final String token) {
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(keyProvider.provideKey())
                    .parseClaimsJws(token)
                    .getBody();
            return ClaimsDecodedToken.builder()
                    .token(token)
                    .claims(claims)
                    .build();
        } catch (final ExpiredJwtException e) {
            throw new TokenExpiredException(e);
        }
    }

    private JwtBuilder createJwtBuilder(final TokenRequest tokenRequest) {
        final Instant issuedAt = timeService.now();
        final JwtBuilder builder = Jwts.builder()
                .setId(tokenRequest.getId())
                .setIssuedAt(Date.from(issuedAt))
                .setSubject(tokenRequest.getSubject())
                .setIssuer(issuer)
                .signWith(keyProvider.provideAlgorithm(), keyProvider.provideKey());
        final Optional<Long> timeToLive = tokenRequest.getTimeToLiveInSeconds();
        if (timeToLive.isPresent()) {
            System.out.println("setting expiry");
            return builder.setExpiration(calculateExpiry(issuedAt, timeToLive.get()));
        }
        return builder;
    }

    private String buildAndLogToken(final JwtBuilder builder) {
        final String token = builder.compact();
        log.info("returning token {}", token);
        return token;
    }

    private static Date calculateExpiry(final Instant issuedAt, final Long timeToLive) {
        return Date.from(issuedAt.plusSeconds(timeToLive));
    }

}
