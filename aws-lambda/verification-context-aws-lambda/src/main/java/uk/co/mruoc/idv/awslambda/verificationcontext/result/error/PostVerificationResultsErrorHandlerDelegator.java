package uk.co.mruoc.idv.awslambda.verificationcontext.result.error;

import uk.co.mruoc.idv.awslambda.ErrorHandlerDelegator;
import uk.co.mruoc.idv.awslambda.InternalServerErrorHandler;

import java.util.Collections;

public class PostVerificationResultsErrorHandlerDelegator extends ErrorHandlerDelegator {

    public PostVerificationResultsErrorHandlerDelegator() {
        super(new InternalServerErrorHandler(), Collections.emptyList());
    }

}
