package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextService;

public interface VerificationContextServiceFactory {

    VerificationContextService build();

}
