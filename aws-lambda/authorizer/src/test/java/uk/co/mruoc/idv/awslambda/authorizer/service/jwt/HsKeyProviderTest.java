package uk.co.mruoc.idv.awslambda.authorizer.service.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.authorizer.service.KeyProvider;

import java.security.Key;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.SignatureAlgorithm.HS384;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static org.assertj.core.api.Assertions.assertThat;

public class HsKeyProviderTest {

    private static final String SECRET_KEY = "my-secret-key";
    private static final String EXPECTED_FORMAT = "RAW";
    private static final byte[] EXPECTED_KEY_BYTES = new byte [] {-101, 43, 30, 114, -73, -83};

    @Test
    public void shouldUseHs256KeyByDefaultIfSignatureAlgorithmNotSupplied() {
        final SignatureAlgorithm defaultAlgorithm = HS256;
        final KeyProvider provider = new HsKeyProvider(SECRET_KEY);

        final Key key = provider.provideKey();

        assertThat(provider.provideAlgorithm()).isEqualTo(defaultAlgorithm);
        assertThat(key.getAlgorithm()).isEqualTo(defaultAlgorithm.getJcaName());
        assertThat(key.getFormat()).isEqualTo(EXPECTED_FORMAT);
        assertThat(key.getEncoded()).contains(EXPECTED_KEY_BYTES);
    }

    @Test
    public void shouldUseHs384SignatureAlgorithm() {
        final SignatureAlgorithm algorithm = HS384;
        final KeyProvider provider = new HsKeyProvider(SECRET_KEY, algorithm);

        final Key key = provider.provideKey();

        assertThat(provider.provideAlgorithm()).isEqualTo(algorithm);
        assertThat(key.getAlgorithm()).isEqualTo(algorithm.getJcaName());
        assertThat(key.getFormat()).isEqualTo(EXPECTED_FORMAT);
        assertThat(key.getEncoded()).contains(EXPECTED_KEY_BYTES);
    }

    @Test
    public void shouldUseHs512SignatureAlgorithm() {
        final SignatureAlgorithm algorithm = HS512;
        final KeyProvider provider = new HsKeyProvider(SECRET_KEY, algorithm);

        final Key key = provider.provideKey();

        assertThat(provider.provideAlgorithm()).isEqualTo(algorithm);
        assertThat(key.getAlgorithm()).isEqualTo(algorithm.getJcaName());
        assertThat(key.getFormat()).isEqualTo(EXPECTED_FORMAT);
        assertThat(key.getEncoded()).contains(EXPECTED_KEY_BYTES);
    }

}
