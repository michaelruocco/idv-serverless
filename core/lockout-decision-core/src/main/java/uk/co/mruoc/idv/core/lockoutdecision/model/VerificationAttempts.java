package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Stream;

@Builder
@ToString
public class VerificationAttempts implements Iterable<VerificationAttempt> {

    private final UUID idvId;

    private final UUID lockoutStateId;

    @Builder.Default
    private final Collection<VerificationAttempt> attempts = new ArrayList<>();

    @Override
    public Iterator<VerificationAttempt> iterator() {
        return attempts.iterator();
    }

    public UUID getLockoutStateId() {
        return lockoutStateId;
    }

    public UUID getIdvId() {
        return idvId;
    }

    public Collection<VerificationAttempt> getAttempts() {
        return Collections.unmodifiableCollection(attempts);
    }

    public VerificationAttempt get(final int index) {
        return new ArrayList<>(attempts).get(index);
    }

    public int size() {
        return attempts.size();
    }

    public Stream<VerificationAttempt> stream() {
        return attempts.stream();
    }

    public VerificationAttempts add(final VerificationAttempt attempt) {
        final Collection<VerificationAttempt> newAttempts = new ArrayList<>(attempts);
        newAttempts.add(attempt);
        return new VerificationAttempts(idvId, lockoutStateId, newAttempts);
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

}
