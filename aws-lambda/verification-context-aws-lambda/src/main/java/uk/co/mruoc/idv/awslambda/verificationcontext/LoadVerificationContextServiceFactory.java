package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.core.verificationcontext.service.LoadVerificationContextService;

public interface LoadVerificationContextServiceFactory {
    LoadVerificationContextService build();
}
