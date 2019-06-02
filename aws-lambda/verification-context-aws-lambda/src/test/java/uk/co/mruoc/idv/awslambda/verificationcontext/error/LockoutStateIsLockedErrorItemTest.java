package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultLockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.TimeBasedLockoutState;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class LockoutStateIsLockedErrorItemTest {

    private static final int LOCKED = 423;

    private static final UUID IDV_ID = UUID.randomUUID();
    private static final int NUMBER_OF_ATTEMPTS = 3;
    private final LockoutState lockoutState = buildLockoutState(IDV_ID);
    private static final String DETAIL = String.format("Identity %s is locked out", IDV_ID);

    private final JsonApiErrorItem item = new LockoutStateIsLockedErrorItem(lockoutState);

    @Test
    public void shouldReturnLockedStatusCode() {
        assertThat(item.getStatusCode()).isEqualTo(LOCKED);
    }

    @Test
    public void shouldReturnLockedStatus() {
        assertThat(item.getStatus()).isEqualTo(Integer.toString(LOCKED));
    }

    @Test
    public void shouldReturnTitle() {
        assertThat(item.getTitle()).isEqualTo("Identity locked out");
    }

    @Test
    public void shouldReturnDetail() {
        assertThat(item.getDetail()).isEqualTo(DETAIL);
    }

    @Test
    public void shouldReturnMeta() {
        final Map<String, Object> meta = item.getMeta();

        assertThat(meta).hasSize(2);
        assertThat(meta).containsEntry("idvId", IDV_ID);
        assertThat(meta).containsEntry("numberOfAttempts", NUMBER_OF_ATTEMPTS);
    }

    @Test
    public void shouldReturnTimeBasedMeta() {
        final Instant lockedUntil = Instant.now();
        final TimeBasedLockoutState lockoutState = buildTimeBasedLockoutState(IDV_ID, lockedUntil);

        final JsonApiErrorItem timeBasedItem = new LockoutStateIsLockedErrorItem(lockoutState);

        final Map<String, Object> meta = timeBasedItem.getMeta();

        assertThat(meta).hasSize(4);
        assertThat(meta).containsEntry("idvId", IDV_ID);
        assertThat(meta).containsEntry("numberOfAttempts", NUMBER_OF_ATTEMPTS);
        assertThat(meta).containsEntry("duration", lockoutState.getDurationInMillis());
        assertThat(meta).containsEntry("lockedUntil", lockedUntil);
    }

    private static LockoutState buildLockoutState(final UUID idvId) {
        final LockoutState lockoutState = mock(DefaultLockoutState.class);
        given(lockoutState.getIdvId()).willReturn(idvId);
        given(lockoutState.getNumberOfAttempts()).willReturn(NUMBER_OF_ATTEMPTS);
        return lockoutState;
    }

    private static TimeBasedLockoutState buildTimeBasedLockoutState(final UUID idvId, final Instant lockedUntil) {
        final TimeBasedLockoutState lockoutState = mock(TimeBasedLockoutState.class);
        given(lockoutState.getIdvId()).willReturn(idvId);
        given(lockoutState.isTimeBased()).willReturn(true);
        given(lockoutState.getNumberOfAttempts()).willReturn(NUMBER_OF_ATTEMPTS);
        given(lockoutState.getDurationInMillis()).willReturn(Duration.ofMinutes(15).toMillis());
        given(lockoutState.getLockedUntil()).willReturn(lockedUntil);
        return lockoutState;
    }

}
