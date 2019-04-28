package uk.co.mruoc.idv.dao.verificationcontext;

import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultsDao;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultsDaoFactory;

public class FakeVerificationResultsDaoFactory implements VerificationResultsDaoFactory {

    private final VerificationResultsDao dao = new FakeVerificationResultsDao();

    @Override
    public VerificationResultsDao build() {
        return dao;
    }

}
