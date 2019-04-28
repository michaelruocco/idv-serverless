package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService;

public interface GetVerificationContextServiceFactory {
    GetVerificationContextService build();
}
