package uk.co.mruoc.idv.dao.verificationattempts;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeVerificationAttemptsDaoTest {


    private final VerificationAttemptsDao dao = new FakeVerificationAttemptsDao();

    @Test
    public void shouldSaveAttemptsAndLoadByIdvId() {
        final UUID idvId = UUID.randomUUID();

        final VerificationAttempts attempts = VerificationAttempts.builder()
                .idvId(idvId)
                .build();

        dao.save(attempts);

        assertThat(dao.loadByIdvId(idvId)).isEqualTo(Optional.of(attempts));
    }

    @Test
    public void shouldSaveAttemptsAndLoadByContextId() {
        final UUID idvId = UUID.randomUUID();
        final VerificationAttempt attempt1 = VerificationAttempt.builder()
                .contextId(UUID.randomUUID())
                .build();
        final VerificationAttempt attempt2 = VerificationAttempt.builder()
                .contextId(UUID.randomUUID())
                .build();

        final VerificationAttempts attempts = VerificationAttempts.builder()
                .idvId(idvId)
                .attempts(Arrays.asList(attempt1, attempt2))
                .build();

        dao.save(attempts);

        final Optional<VerificationAttempts> attempts1 = dao.loadByContextId(attempt1.getContextId());
        final Optional<VerificationAttempts> attempts2 = dao.loadByContextId(attempt2.getContextId());

        assertThat(attempts1.get()).containsExactly(attempt1);
        assertThat(attempts2.get()).containsExactly(attempt2);
    }

    @Test
    public void shouldReturnEmptyOptionalIfAttemptsNotFoundForIdvId() {
        final Optional<VerificationAttempts> attempts = dao.loadByIdvId(UUID.randomUUID());

        assertThat(attempts).isEmpty();
    }

}
