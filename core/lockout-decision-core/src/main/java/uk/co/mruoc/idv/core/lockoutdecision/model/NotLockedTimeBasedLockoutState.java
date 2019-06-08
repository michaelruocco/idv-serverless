package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Getter
public class NotLockedTimeBasedLockoutState extends DefaultLockoutState implements TimeBasedLockoutState {

    private static final boolean NOT_LOCKED = false;

    public NotLockedTimeBasedLockoutState(final VerificationAttempts attempts, final String type) {
        super(attempts, type, NOT_LOCKED);
    }

    @Override
    public Optional<Duration> getDuration() {
        return Optional.empty();
    }

    @Override
    public Optional<Long> getDurationInMillis() {
        return Optional.empty();
    }

    @Override
    public Optional<Instant> getLockedUntil() {
        return Optional.empty();
    }

}
