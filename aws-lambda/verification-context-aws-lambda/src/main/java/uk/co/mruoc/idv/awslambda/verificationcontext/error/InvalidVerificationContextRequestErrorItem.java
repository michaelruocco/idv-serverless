package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collections;

public class InvalidVerificationContextRequestErrorItem extends JsonApiErrorItem {

    private static final int STATUS_CODE = 400;
    private static final String TITLE = "Bad Request";
    private static final String DETAIL_FORMAT = "Verification context request is invalid: %s";

    public InvalidVerificationContextRequestErrorItem(final String detail) {
        super(TITLE, buildDetail(detail), Collections.emptyMap(), STATUS_CODE);
    }

    private static String buildDetail(final String detail) {
        return String.format(DETAIL_FORMAT, detail);
    }

}
