package uk.co.mruoc.idv.awslambda.identity.error;

import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AliasTypeNotSupportedErrorItem extends JsonApiErrorItem {

    private static final int STATUS_CODE = 422;
    private static final String TITLE = "Alias type not supported";
    private static final String DETAIL_TEMPLATE = "Alias type %s is not supported for channel %s";

    public AliasTypeNotSupportedErrorItem(final String aliasType, final String channelId) {
        super(TITLE, toDetail(aliasType, channelId), toMeta(aliasType, channelId), STATUS_CODE);
    }

    private static String toDetail(final String aliasType, final String channelId) {
        return String.format(DETAIL_TEMPLATE, aliasType, channelId);
    }

    private static Map<String, Object> toMeta(final String aliasType, final String channelId) {
        final Map<String, Object> meta = new HashMap<>();
        meta.put("aliasType", aliasType);
        meta.put("channelId", channelId);
        return Collections.unmodifiableMap(meta);
    }

}
