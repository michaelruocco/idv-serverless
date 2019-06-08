package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Getter
public class LockedTimeBasedLockoutState extends DefaultLockoutState implements TimeBasedLockoutState {

    private static final boolean LOCKED = true;

    private final Duration duration;
    private final Instant lockedUntil;

    public LockedTimeBasedLockoutState(final VerificationAttempts attempts,
                                       final String type,
                                       final Duration duration,
                                       final Instant lockedUntil) {
        super(attempts, type, LOCKED);
        this.duration = duration;
        this.lockedUntil = lockedUntil;
    }

    @Override
    public Optional<Duration> getDuration() {
        return Optional.of(duration);
    }

    @Override
    public Optional<Long> getDurationInMillis() {
        return Optional.of(duration.toMillis());
    }

    @Override
    public Optional<Instant> getLockedUntil() {
        return Optional.of(lockedUntil);
    }

}
