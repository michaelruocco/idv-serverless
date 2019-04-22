package uk.co.mruoc.idv.dao.verificationcontext;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationResultsDao;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeVerificationResultsDaoTest {

    private final UUID contextId = UUID.randomUUID();

    private final VerificationResultsDao dao = new FakeVerificationResultsDao();

    @Test
    public void shouldSaveResults() {
        final VerificationMethodResults results = VerificationMethodResults.builder()
                .verificationContextId(contextId)
                .build();

        dao.save(results);

        assertThat(dao.load(contextId)).isEqualTo(Optional.of(results));
    }

    @Test
    public void shouldReturnEmptyOptionalIfResultsNotFound() {
        final Optional<VerificationMethodResults> results = dao.load(UUID.randomUUID());

        assertThat(results).isEmpty();
    }

}
