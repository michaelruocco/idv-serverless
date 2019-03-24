package uk.co.mruoc.idv.dao.verificationcontext;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDaoFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeVerificationContextDaoFactoryTest {

    private final VerificationContextDaoFactory factory = new FakeVerificationContextDaoFactory();

    @Test
    public void shouldReturnFakeVerificationContextDao() {
        final VerificationContextDao dao = factory.build();

        assertThat(dao).isInstanceOf(FakeVerificationContextDao.class);
    }

}
