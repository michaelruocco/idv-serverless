package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VerificationContextOkResponseFactory extends VerificationContextResponseFactory {

    private static final int OK_STATUS_CODE = 200;

    public VerificationContextOkResponseFactory(final ObjectMapper mapper) {
        super(OK_STATUS_CODE, mapper);
    }

}
