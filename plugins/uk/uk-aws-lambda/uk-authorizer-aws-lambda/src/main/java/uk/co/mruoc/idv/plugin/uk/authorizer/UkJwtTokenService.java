package uk.co.mruoc.idv.plugin.uk.authorizer;

import uk.co.mruoc.idv.awslambda.authorizer.service.jwt.HsKeyProvider;
import uk.co.mruoc.idv.awslambda.authorizer.service.jwt.JwtTokenService;
import uk.co.mruoc.idv.core.service.DefaultTimeService;

public class UkJwtTokenService extends JwtTokenService {

    private static final String ISSUER = "uk-idv";

    public UkJwtTokenService(final String secretKey) {
        super(ISSUER, new HsKeyProvider(secretKey), new DefaultTimeService());
    }

}
