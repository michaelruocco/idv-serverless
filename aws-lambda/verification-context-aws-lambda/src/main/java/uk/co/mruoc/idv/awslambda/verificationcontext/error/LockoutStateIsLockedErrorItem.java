package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.TimeBasedLockoutState;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LockoutStateIsLockedErrorItem extends JsonApiErrorItem {

    private static final int STATUS_CODE = 423;
    private static final String TITLE = "Identity locked out";
    private static final String DETAIL_FORMAT = "Identity %s is locked out";

    public LockoutStateIsLockedErrorItem(final LockoutState lockoutState) {
        super(TITLE, buildDetail(lockoutState), toMeta(lockoutState), STATUS_CODE);
    }

    private static String buildDetail(final LockoutState lockoutState) {
        final UUID idvId = lockoutState.getIdvId();
        return String.format(DETAIL_FORMAT, idvId);
    }


    private static Map<String, Object> toMeta(final LockoutState lockoutState) {
        final Map<String, Object> meta = new HashMap<>();
        meta.put("idvId", lockoutState.getIdvId());
        meta.put("numberOfAttempts", lockoutState.getNumberOfAttempts());
        if (lockoutState.isTimeBased()) {
            final TimeBasedLockoutState timeBasedState = (TimeBasedLockoutState) lockoutState;
            meta.put("duration", timeBasedState.getDurationInMillis());
            meta.put("lockedUntil", timeBasedState.getLockedUntil());
        }
        return Collections.unmodifiableMap(meta);
    }

}
