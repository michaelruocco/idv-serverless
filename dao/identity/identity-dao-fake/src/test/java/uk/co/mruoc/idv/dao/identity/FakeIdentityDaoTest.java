package uk.co.mruoc.idv.dao.identity;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeIdentityDaoTest {

    private final Alias idvId = new IdvIdAlias();
    private final Alias alias = new TokenizedCreditCardNumberAlias("1111111111111111");

    private final IdentityDao dao = new FakeIdentityDao();

    @Test
    public void shouldSaveIdentity() {
        final Identity identity = Identity.withAliases(idvId, alias);

        dao.save(identity);

        Optional<Identity> expectedIdentity = Optional.of(identity);
        assertThat(dao.load(idvId)).isEqualTo(expectedIdentity);
        assertThat(dao.load(alias)).isEqualTo(expectedIdentity);
    }

    @Test
    public void shouldDeleteIdentity() {
        final Identity identity = Identity.withAliases(idvId, alias);
        dao.save(identity);

        dao.delete(identity);

        assertThat(dao.load(idvId)).isEmpty();
        assertThat(dao.load(alias)).isEmpty();
    }

}
