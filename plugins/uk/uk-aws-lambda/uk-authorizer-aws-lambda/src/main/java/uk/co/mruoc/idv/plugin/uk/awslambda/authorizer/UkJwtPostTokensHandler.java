package uk.co.mruoc.idv.plugin.uk.awslambda.authorizer;

import uk.co.mruoc.idv.awslambda.authorizer.handler.PostTokensHandler;

public class UkJwtPostTokensHandler extends PostTokensHandler {

    private static final String SECRET_KEY = System.getenv("AUTHORIZER_SECRET_KEY");

    public UkJwtPostTokensHandler() {
        super(new UkJwtTokenService(SECRET_KEY));
    }

}
