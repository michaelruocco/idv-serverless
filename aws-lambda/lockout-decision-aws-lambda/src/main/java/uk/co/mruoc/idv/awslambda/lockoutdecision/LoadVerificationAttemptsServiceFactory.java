package uk.co.mruoc.idv.awslambda.lockoutdecision;

import uk.co.mruoc.idv.core.lockoutdecision.service.LoadVerificationAttemptsService;

public interface LoadVerificationAttemptsServiceFactory {

    LoadVerificationAttemptsService build();

}