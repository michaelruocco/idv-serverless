package uk.co.mruoc.idv.awslambda.lockoutdecision;

import uk.co.mruoc.idv.core.lockoutdecision.service.LoadVerificationAttemptsService;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutStateService;

public interface LoadVerificationAttemptsServiceFactory {

    LoadVerificationAttemptsService build();

}
