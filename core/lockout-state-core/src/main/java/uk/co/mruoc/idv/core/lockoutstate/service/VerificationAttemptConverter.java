package uk.co.mruoc.idv.core.lockoutstate.service;

import uk.co.mruoc.idv.core.lockoutstate.model.DefaultLockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;

public class VerificationAttemptConverter {

    public LockoutStateRequest toLockoutStateRequest(final VerificationAttempt attempt) {
        return DefaultLockoutStateRequest.builder()
                .channelId(attempt.getChannelId())
                .activityType(attempt.getActivityType())
                .alias(attempt.getAlias())
                .build();
    }

}
