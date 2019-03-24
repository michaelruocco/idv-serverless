package uk.co.mruoc.idv.dao.identity;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.identity.service.IdentityDaoFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeIdentityDaoFactoryTest {

    private final IdentityDaoFactory factory = new FakeIdentityDaoFactory();

    @Test
    public void shouldReturnFakeIdentityDao() {
        final IdentityDao dao = factory.build();

        assertThat(dao).isInstanceOf(FakeIdentityDao.class);
    }

}
