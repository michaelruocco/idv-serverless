package uk.co.mruoc.idv.dao.verificationcontext;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultsDao;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultsDaoFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeVerificationResultsDaoFactoryTest {

    private final VerificationResultsDaoFactory factory = new FakeVerificationResultsDaoFactory();

    @Test
    public void shouldReturnFakeVerificationResultDao() {
        final VerificationResultsDao dao = factory.build();

        assertThat(dao).isInstanceOf(FakeVerificationResultsDao.class);
    }

}
