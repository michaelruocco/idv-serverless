package uk.co.mruoc.idv.awslambda.authorizer.service.jwt;

import io.jsonwebtoken.Claims;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.authorizer.service.DecodedToken;

import java.sql.Date;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ClaimsDecodedTokenTest {

    private static final String TOKEN = "my-token";

    private final Claims claims = mock(Claims.class);

    private final DecodedToken decodedToken = ClaimsDecodedToken.builder()
            .token(TOKEN)
            .claims(claims)
            .build();

    @Test
    public void shouldReturnToken() {
        assertThat(decodedToken.getToken()).isEqualTo(TOKEN);
    }

    @Test
    public void shouldReturnId() {
        final String id = "id";
        given(claims.getId()).willReturn(id);

        assertThat(decodedToken.getId()).isEqualTo(id);
    }

    @Test
    public void shouldReturnIssuer() {
        final String issuer = "issuer";
        given(claims.getIssuer()).willReturn(issuer);

        assertThat(decodedToken.getIssuer()).isEqualTo(issuer);
    }

    @Test
    public void shouldReturnSubject() {
        final String subject = "subject";
        given(claims.getSubject()).willReturn(subject);

        assertThat(decodedToken.getSubject()).isEqualTo(subject);
    }

    @Test
    public void shouldReturnAudience() {
        final String audience = "audience";
        given(claims.getAudience()).willReturn(audience);

        assertThat(decodedToken.getAudience()).isEqualTo(audience);
    }

    @Test
    public void shouldReturnIssuedAt() {
        final Instant issuedAt = Instant.now();
        given(claims.getIssuedAt()).willReturn(Date.from(issuedAt));

        assertThat(decodedToken.getIssuedAt()).isEqualTo(issuedAt);
    }

    @Test
    public void shouldEmptyOptionalIfNoExiryDate() {
        assertThat(decodedToken.getExpiration()).isEmpty();
    }

    @Test
    public void shouldReturnExpiration() {
        final Instant expiration = Instant.now();
        given(claims.getExpiration()).willReturn(Date.from(expiration));

        assertThat(decodedToken.getExpiration()).contains(expiration);
    }

}
