package uk.co.mruoc.idv.dao.verificationattempts;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeVerificationAttemptsDaoTest {


    private final VerificationAttemptsDao dao = new FakeVerificationAttemptsDao();

    @Test
    public void shouldSaveAttempts() {
        final UUID idvId = UUID.randomUUID();

        final VerificationAttempts attempts = VerificationAttempts.builder()
                .idvId(idvId)
                .build();

        dao.save(attempts);

        assertThat(dao.loadByIdvId(idvId)).isEqualTo(Optional.of(attempts));
    }

    @Test
    public void shouldReturnEmptyOptionalIfAttemptsNotFoundForIdvId() {
        final Optional<VerificationAttempts> attempts = dao.loadByIdvId(UUID.randomUUID());

        assertThat(attempts).isEmpty();
    }

}