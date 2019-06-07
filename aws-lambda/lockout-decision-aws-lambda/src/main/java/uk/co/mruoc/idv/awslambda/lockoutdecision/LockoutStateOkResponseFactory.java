package uk.co.mruoc.idv.awslambda.lockoutdecision;

import uk.co.mruoc.idv.json.JsonConverter;

public class LockoutStateOkResponseFactory extends LockoutStateResponseFactory {

    private static final int OK_STATUS_CODE = 200;

    public LockoutStateOkResponseFactory(final JsonConverter converter) {
        super(OK_STATUS_CODE, converter);
    }

}
