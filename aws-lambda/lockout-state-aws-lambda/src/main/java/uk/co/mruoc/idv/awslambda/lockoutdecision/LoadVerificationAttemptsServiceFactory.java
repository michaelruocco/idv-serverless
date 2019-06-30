package uk.co.mruoc.idv.awslambda.lockoutdecision;

import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationattempts.service.VerificationAttemptsService;

public interface LoadVerificationAttemptsServiceFactory {

    VerificationAttemptsService build();

    VerificationAttemptsDao getDao();

}
