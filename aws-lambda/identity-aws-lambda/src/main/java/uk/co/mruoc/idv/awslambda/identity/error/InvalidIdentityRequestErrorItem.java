package uk.co.mruoc.idv.awslambda.identity.error;

import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collections;

public class InvalidIdentityRequestErrorItem extends JsonApiErrorItem {

    private static final int STATUS_CODE = 400;
    private static final String TITLE = "Bad Request";
    private static final String DETAIL = "Either IDV ID or aliasType and aliasValue must be provided";

    public InvalidIdentityRequestErrorItem() {
        super(TITLE, DETAIL, Collections.emptyMap(), STATUS_CODE);
    }

}
