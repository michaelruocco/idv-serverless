package uk.co.mruoc.idv.dao.verificationcontext;

import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDaoFactory;

public class FakeVerificationContextDaoFactory implements VerificationContextDaoFactory {

    private final VerificationContextDao dao = new FakeVerificationContextDao();

    @Override
    public VerificationContextDao build() {
        return dao;
    }

}
