package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VerificationPolicyNotConfiguredForActivityErrorItem extends JsonApiErrorItem {

    private static final int STATUS_CODE = 422;
    private static final String TITLE = "No verification policies configured";
    private static final String DETAIL_FORMAT = "No verification policies are configured for channel %s and activity %s";

    public VerificationPolicyNotConfiguredForActivityErrorItem(final String channelId, final String activityType) {
        super(TITLE, buildDetail(channelId, activityType), toMeta(channelId, activityType), STATUS_CODE);
    }

    private static String buildDetail(final String channelId, final String activityType) {
        return String.format(DETAIL_FORMAT, channelId, activityType);
    }


    private static Map<String, Object> toMeta(final String channelId, final String activityType) {
        final Map<String, Object> meta = new HashMap<>();
        meta.put("channelId", channelId);
        meta.put("activityType", activityType);
        return Collections.unmodifiableMap(meta);
    }

}
