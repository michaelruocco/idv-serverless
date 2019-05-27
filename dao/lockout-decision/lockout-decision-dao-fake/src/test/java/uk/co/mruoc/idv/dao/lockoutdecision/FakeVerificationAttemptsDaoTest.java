package uk.co.mruoc.idv.dao.lockoutdecision;

import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeVerificationAttemptsDaoTest {

    private static final UUID IDV_ID = UUID.randomUUID();

    private final VerificationAttemptsDao dao = new FakeVerificationAttemptsDao();

    @Test
    public void shouldSaveAttempts() {

        final VerificationAttempts attempts = VerificationAttempts.builder()
                .idvId(IDV_ID)
                .build();

        dao.save(attempts);

        assertThat(dao.loadByIdvId(IDV_ID)).isEqualTo(Optional.of(attempts));
    }

    @Test
    public void shouldReturnEmptyOptionalIfAttemptsNotFoundForIdvId() {
        final Optional<VerificationAttempts> attempts = dao.loadByIdvId(UUID.randomUUID());

        assertThat(attempts).isEmpty();
    }

}
