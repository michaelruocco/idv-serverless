package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VerificationAttemptsTest {

    @Test
    public void shouldSetIdvId() {
        final UUID expectedIdvId = UUID.randomUUID();
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .idvId(expectedIdvId)
                .build();

        final UUID idvId = attempts.getIdvId();

        assertThat(idvId).isEqualTo(expectedIdvId);
    }

    @Test
    public void shouldSetLockoutStateId() {
        final UUID expectedId = UUID.randomUUID();
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .lockoutStateId(expectedId)
                .build();

        final UUID id = attempts.getLockoutStateId();

        assertThat(id).isEqualTo(expectedId);
    }

    @Test
    public void shouldReturnEmptyListOfAttemptsIfNotSet() {
        final VerificationAttempts attempts = VerificationAttempts.builder().build();

        final int size = attempts.size();

        assertThat(size).isEqualTo(0);
    }

    @Test
    public void shouldReturnNumberOfAttemptsAsSize() {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final Collection<VerificationAttempt> attemptCollection = Arrays.asList(attempt, attempt);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(attemptCollection)
                .build();

        final int size = attempts.size();

        assertThat(size).isEqualTo(attemptCollection.size());
    }

    @Test
    public void shouldShouldAddAttempt() {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final VerificationAttempts attempts = VerificationAttempts.builder().build();

        final VerificationAttempts updatedAttempts = attempts.add(attempt);

        assertThat(updatedAttempts.size()).isEqualTo(1);
    }

    @Test
    public void shouldReturnMostRecentAttemptTimestamp() {
        final Instant now = Instant.now();
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        final Instant timestamp1 = now.plusSeconds(20);
        given(attempt1.getTimestamp()).willReturn(timestamp1);

        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);
        given(attempt2.getTimestamp()).willReturn(now);

        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();

        final Instant mostRecentTimestamp = attempts.getMostRecentTimestamp();

        assertThat(mostRecentTimestamp).isEqualTo(timestamp1);
    }

    @Test
    public void shouldRemoveAttempts() {
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();

        final VerificationAttempts attemptsRemoved = attempts.remove(attempt1);

        assertThat(attemptsRemoved.size()).isEqualTo(1);
    }

    @Test
    public void shouldReturnAttemptStream() {
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();

        final Stream<VerificationAttempt> stream = attempts.stream();

        assertThat(stream).containsExactly(attempt1, attempt2);
    }

    @Test
    public void shouldReturnAttempts() {
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();

        final Collection<VerificationAttempt> attemptsCollection = attempts.toCollection();

        assertThat(attemptsCollection).containsExactly(attempt1, attempt2);
    }

    @Test
    public void shouldReturnAliases() {
        final Alias alias1 = mock(Alias.class);
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        given(attempt1.getAlias()).willReturn(alias1);

        final Alias alias2 = mock(Alias.class);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);
        given(attempt2.getAlias()).willReturn(alias2);

        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();

        final Aliases aliases = attempts.getAliases();

        assertThat(aliases).containsExactlyInAnyOrder(alias1, alias2);
    }

    @Test
    public void shouldGetAttemptByIndex() {
        final VerificationAttempt attempt1 = mock(VerificationAttempt.class);
        final VerificationAttempt attempt2 = mock(VerificationAttempt.class);
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();

        assertThat(attempts.get(0)).isEqualTo(attempt1);
        assertThat(attempts.get(1)).isEqualTo(attempt2);
    }

    @Test
    public void shouldReturnDetails() {
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .build();

        assertThat(attempts.toString()).isEqualTo("VerificationAttempts(idvId=null, lockoutStateId=null, attempts=[])");
    }

    @Test
    public void shouldHaveNoArgsConstructorForJackson() {
        final VerificationAttempts attempts = new VerificationAttempts();

        assertThat(attempts).isNotNull();
    }

}
