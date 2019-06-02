package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LockoutPolicyNotConfiguredForChannelErrorItem extends JsonApiErrorItem {

    private static final int STATUS_CODE = 422;
    private static final String TITLE = "No lockout policies configured";
    private static final String DETAIL_FORMAT = "No lockout policies are configured for channel %s";

    public LockoutPolicyNotConfiguredForChannelErrorItem(final String channelId) {
        super(TITLE, buildDetail(channelId), toMeta(channelId), STATUS_CODE);
    }

    private static String buildDetail(final String channelId) {
        return String.format(DETAIL_FORMAT, channelId);
    }


    private static Map<String, Object> toMeta(final String channelId) {
        final Map<String, Object> meta = new HashMap<>();
        meta.put("channelId", channelId);
        return Collections.unmodifiableMap(meta);
    }

}
