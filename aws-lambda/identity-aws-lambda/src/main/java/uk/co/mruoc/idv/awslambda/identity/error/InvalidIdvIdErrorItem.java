package uk.co.mruoc.idv.awslambda.identity.error;

import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InvalidIdvIdErrorItem extends JsonApiErrorItem {

    private static final int STATUS_CODE = 400;
    private static final String TITLE = "Invalid IDV ID";
    private static final String DETAIL_TEMPLATE = "IDV ID value is invalid %s it must be a valid UUID";

    public InvalidIdvIdErrorItem(final String value) {
        super(TITLE, toDetail(value), toMeta(value), STATUS_CODE);
    }

    private static String toDetail(final String value) {
        return String.format(DETAIL_TEMPLATE, value);
    }

    private static Map<String, Object> toMeta(final String value) {
        final Map<String, Object> meta = new HashMap<>();
        meta.put("value", value);
        return Collections.unmodifiableMap(meta);
    }

}
