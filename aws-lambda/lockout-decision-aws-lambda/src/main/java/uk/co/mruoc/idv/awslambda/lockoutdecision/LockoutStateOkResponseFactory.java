package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.json.JsonConverter;

public class VerificationContextOkResponseFactory extends VerificationContextResponseFactory {

    private static final int OK_STATUS_CODE = 200;

    public VerificationContextOkResponseFactory(final JsonConverter converter) {
        super(OK_STATUS_CODE, converter);
    }

}
