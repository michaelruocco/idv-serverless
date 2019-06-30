package uk.co.mruoc.idv.awslambda.lockoutdecision.error;

import uk.co.mruoc.idv.awslambda.ErrorHandlerDelegator;
import uk.co.mruoc.idv.awslambda.InternalServerErrorHandler;

import java.util.Collections;

public class PostVerificationAttemptErrorHandlerDelegator extends ErrorHandlerDelegator {

    public PostVerificationAttemptErrorHandlerDelegator() {
        super(new InternalServerErrorHandler(), Collections.emptyList());
    }

}
