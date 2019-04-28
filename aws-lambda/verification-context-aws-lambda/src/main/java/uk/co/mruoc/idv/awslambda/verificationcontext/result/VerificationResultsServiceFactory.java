package uk.co.mruoc.idv.awslambda.verificationcontext.result;

import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultService;

public interface VerificationResultsServiceFactory {

    VerificationResultService build();

}
