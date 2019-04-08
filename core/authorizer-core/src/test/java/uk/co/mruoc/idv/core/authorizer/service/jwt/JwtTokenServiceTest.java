package uk.co.mruoc.idv.core.authorizer.service.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.Test;
import uk.co.mruoc.idv.core.authorizer.model.DecodedToken;
import uk.co.mruoc.idv.core.authorizer.model.TokenRequest;
import uk.co.mruoc.idv.core.authorizer.model.TokenResponse;
import uk.co.mruoc.idv.core.authorizer.service.DefaultTokenRequest;
import uk.co.mruoc.idv.core.authorizer.service.KeyProvider;
import uk.co.mruoc.idv.core.authorizer.service.TokenService.TokenExpiredException;
import uk.co.mruoc.idv.core.service.TimeService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class JwtTokenServiceTest {

    private static final UUID ID = UUID.fromString("0a51a92b-19b9-4354-9c13-3cf640538115");
    private static final String ISSUER = "ISSUER";
    private static final String SUBJECT = "my-subject";
    private static final Instant NOW = Instant.parse("2019-04-07T06:49:38.106Z");

    private static final String NON_EXPIRING_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwYTUxYTkyYi0xOWI5LTQzNTQtOWM" +
            "xMy0zY2Y2NDA1MzgxMTUiLCJpYXQiOjE1NTQ2MTk3NzgsInN1YiI6Im15LXN1YmplY3QiLCJpc3MiOiJJU1NVRVIifQ.sAArOrM_r" +
            "dxuS5FMZmIdImbE8gyHbb7wWgwnn8s84sc";

    private static final String EXPIRING_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwYTUxYTkyYi0xOWI5LTQzNTQtOWMxMy0" +
            "zY2Y2NDA1MzgxMTUiLCJpYXQiOjE1NTQ2MTk3NzgsInN1YiI6Im15LXN1YmplY3QiLCJpc3MiOiJJU1NVRVIiLCJleHAiOjE1NTQ2" +
            "MTk4Mzh9.sTHGXkEdTNH9paRTCF0dnPBavftLvC-2Rj1SUbIOPrQ";

    private final KeyProvider keyProvider = new HsKeyProvider("my-secret-key");
    private final TimeService timeService = mock(TimeService.class);

    private final JwtTokenService service = new JwtTokenService(ISSUER, keyProvider, timeService);

    @Test
    public void shouldCreateNonExpiringTokenIfTimeToLiveNotSpecified() {
        final TokenRequest request = DefaultTokenRequest.builder()
                .id(ID)
                .subject(SUBJECT)
                .build();
        given(timeService.now()).willReturn(NOW);

        final TokenResponse response = service.create(request);

        assertThat(response.getToken()).isEqualTo(NON_EXPIRING_TOKEN);
    }

    @Test
    public void shouldDecodeNonExpiringToken() {
        final DecodedToken decodedToken = service.decode(NON_EXPIRING_TOKEN);

        assertThat(decodedToken.getToken()).isEqualTo(NON_EXPIRING_TOKEN);
        assertThat(decodedToken.getId()).isEqualTo(ID.toString());
        assertThat(decodedToken.getIssuer()).isEqualTo(ISSUER);
        assertThat(decodedToken.getSubject()).isEqualTo(SUBJECT);
        assertThat(decodedToken.getIssuedAt()).isEqualTo(NOW.truncatedTo(ChronoUnit.SECONDS));
        assertThat(decodedToken.getAudience()).isNull();
        assertThat(decodedToken.getExpiration()).isEmpty();
    }

    @Test
    public void shouldCreateExpiringTokenIfTimeToLiveSpecified() {
        final TokenRequest request = DefaultTokenRequest.builder()
                .id(ID)
                .subject(SUBJECT)
                .timeToLiveInSeconds(60L)
                .build();
        given(timeService.now()).willReturn(NOW);

        final TokenResponse response = service.create(request);

        assertThat(response.getToken()).isEqualTo(EXPIRING_TOKEN);
    }

    @Test
    public void shouldThrowExceptionIfAttemptToDecodeExpiredToken() {
        final Throwable cause = catchThrowable(() -> service.decode(EXPIRING_TOKEN));

        assertThat(cause).isInstanceOf(TokenExpiredException.class)
                .hasCauseInstanceOf(ExpiredJwtException.class);
    }

    @Test
    public void shouldDecodeExpiringTokenIfTokenHasNotExpired() {
        final long timeToLive = 60;
        final TokenRequest request = DefaultTokenRequest.builder()
                .id(ID)
                .subject(SUBJECT)
                .timeToLiveInSeconds(timeToLive)
                .build();
        final Instant now = Instant.now();
        given(timeService.now()).willReturn(now);
        final TokenResponse response = service.create(request);

        final DecodedToken decodedToken = service.decode(response.getToken());

        assertThat(decodedToken.getExpiration()).contains(now.plusSeconds(timeToLive).truncatedTo(ChronoUnit.SECONDS));
    }

}
