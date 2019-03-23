package uk.co.mruoc.idv.awslambda.identity.error;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AliasLoadFailedErrorItem extends JsonApiErrorItem {

    private static final int STATUS_CODE = 422;
    private static final String TITLE = "Failed to load alias";
    private static final String DETAIL_TEMPLATE = "Failed to load alias %s";

    public AliasLoadFailedErrorItem(final Alias alias) {
        super(TITLE, toDetail(alias), toMeta(alias), STATUS_CODE);
    }

    private static String toDetail(final Alias alias) {
        return String.format(DETAIL_TEMPLATE, alias);
    }

    private static Map<String, Object> toMeta(final Alias alias) {
        final Map<String, Object> meta = new HashMap<>();
        meta.put("alias", alias);
        return Collections.unmodifiableMap(meta);
    }
}
