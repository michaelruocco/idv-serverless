package uk.co.mruoc.idv.dao.identity;

import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.identity.service.IdentityDaoFactory;

public class FakeIdentityDaoFactory implements IdentityDaoFactory {

    private final IdentityDao dao = new FakeIdentityDao();

    @Override
    public IdentityDao build() {
        return dao;
    }

}
