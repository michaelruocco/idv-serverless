package uk.co.mruoc.idv.awslambda.identity.error;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.HashMap;
import java.util.Map;

public class IdentityNotFoundErrorItem extends JsonApiErrorItem {

    private static final int STATUS_CODE = 404;
    private static final String TITLE = "Identity not found";
    private static final String DETAIL_TEMPLATE = "Identity not found using alias type %s and %s value %s";

    public IdentityNotFoundErrorItem(final Alias alias) {
        super(TITLE, toDetail(alias), toMeta(alias), STATUS_CODE);
    }

    private static String toDetail(final Alias alias) {
        return String.format(DETAIL_TEMPLATE, alias.getTypeName(), alias.getFormat(), alias.getValue());
    }

    private static Map<String, Object> toMeta(final Alias alias) {
        final Map<String, Object> meta = new HashMap<>();
        meta.put("alias", alias);
        return meta; //Collections.unmodifiableMap(meta);
    }

}
