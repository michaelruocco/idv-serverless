package uk.co.mruoc.idv.core.verificationattempts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor(force = true) //required by jackson
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

    public Collection<VerificationAttempt> toCollection() {
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
        return toNewAttempts(newAttempts);
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
        return toNewAttempts(remainingAttempts);
    }

    private VerificationAttempts toNewAttempts(final Collection<VerificationAttempt> newAttempts) {
        return VerificationAttempts.builder()
                .idvId(idvId)
                .lockoutStateId(lockoutStateId)
                .attempts(newAttempts)
                .build();
    }

}
