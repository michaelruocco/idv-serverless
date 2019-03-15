package uk.co.mruoc.idv.dao.verificationcontext;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeVerificationContextDaoTest {

    private final UUID id = UUID.randomUUID();

    private final VerificationContextDao dao = new FakeVerificationContextDao();

    @Test
    public void shouldSaveContext() {
        final VerificationContext context = VerificationContext.builder()
                .id(id)
                .build();

        dao.save(context);

        assertThat(dao.load(id)).isEqualTo(Optional.of(context));
    }

    @Test
    public void shouldReturnEmptyOptionalIfContextNotFound() {
        final Optional<VerificationContext> context = dao.load(UUID.randomUUID());

        assertThat(context).isEmpty();
    }

}
