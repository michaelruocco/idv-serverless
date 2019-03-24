package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VerificationContextCreatedResponseFactory extends VerificationContextResponseFactory {

    private static final int CREATED_STATUS_CODE = 201;

    public VerificationContextCreatedResponseFactory(final ObjectMapper mapper) {
        super(CREATED_STATUS_CODE, mapper);
    }

}
