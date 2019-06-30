package uk.co.mruoc.idv.core.lockoutstate.model;

import lombok.Getter;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

@Getter
public class NotLockedTimeBasedIntervalLockoutState extends NotLockedTimeBasedLockoutState {

    private static final String TYPE = LockoutType.TIME_BASED_INTERVAL;

    public NotLockedTimeBasedIntervalLockoutState(final VerificationAttempts attempts) {
        super(attempts, TYPE);
    }

}
