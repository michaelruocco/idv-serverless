package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Stream;

@Builder
public class VerificationAttempts implements Iterable<VerificationAttempt> {

    private final UUID idvId;

    private final UUID lockoutStateId;

    @Builder.Default
    private final Collection<VerificationAttempt> attempts = new ArrayList<>();

    public UUID getLockoutStateId() {
        return lockoutStateId;
    }

    public UUID getIdvId() {
        return idvId;
    }

    public int size() {
        return attempts.size();
    }

    public Stream<VerificationAttempt> stream() {
        return attempts.stream();
    }

    public void add(final VerificationAttempt attempt) {
        attempts.add(attempt);
    }

    public Instant getMostRecentTimestamp() {
        Instant latest = Instant.MIN;
        for (final VerificationAttempt attempt : attempts) {
            if (latest.isBefore(attempt.getTimestamp())) {
                latest = attempt.getTimestamp();
            }
        }
        return latest;
    }

    public VerificationAttempts remove(final VerificationAttempt... attemptsToRemove) {
        return removeAll(Arrays.asList(attemptsToRemove));
    }

    public VerificationAttempts removeAll(final Collection<VerificationAttempt> attemptsToRemove) {
        final Collection<VerificationAttempt> remainingAttempts = new ArrayList<>(attempts);
        remainingAttempts.removeAll(attemptsToRemove);
        return new VerificationAttempts(idvId, lockoutStateId, remainingAttempts);
    }

    @Override
    public Iterator<VerificationAttempt> iterator() {
        return attempts.iterator();
    }

}
