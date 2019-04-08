package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.json.JsonConverter;

public class VerificationContextCreatedResponseFactory extends VerificationContextResponseFactory {

    private static final int CREATED_STATUS_CODE = 201;

    public VerificationContextCreatedResponseFactory(final JsonConverter mapper) {
        super(CREATED_STATUS_CODE, mapper);
    }

}
