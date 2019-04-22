package uk.co.mruoc.idv.dao.verificationcontext;

import uk.co.mruoc.idv.core.verificationcontext.service.VerificationResultsDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationResultsDaoFactory;

public class FakeVerificationResultsDaoFactory implements VerificationResultsDaoFactory {

    private final VerificationResultsDao dao = new FakeVerificationResultsDao();

    @Override
    public VerificationResultsDao build() {
        return dao;
    }

}
