package uk.co.mruoc.idv.awslambda.authorizer.service.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.authorizer.service.KeyProvider;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

@Slf4j
public class HsKeyProvider implements KeyProvider {

    private static final SignatureAlgorithm DEFAULT_SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private final Key key;
    private final SignatureAlgorithm signatureAlgorithm;

    public HsKeyProvider(final String secretKey) {
        this(secretKey, DEFAULT_SIGNATURE_ALGORITHM);
    }

    public HsKeyProvider(final String secretKey, final SignatureAlgorithm signatureAlgorithm) {
        this.key = buildKey(secretKey, signatureAlgorithm);
        this.signatureAlgorithm = signatureAlgorithm;
    }

    @Override
    public Key provideKey() {
        return key;
    }

    @Override
    public SignatureAlgorithm provideAlgorithm() {
        return signatureAlgorithm;
    }

    private static Key buildKey(final String secretKey, final SignatureAlgorithm signatureAlgorithm) {
        log.info("building key with secret key {}", secretKey);
        byte[] bytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(bytes, signatureAlgorithm.getJcaName());
    }

}
