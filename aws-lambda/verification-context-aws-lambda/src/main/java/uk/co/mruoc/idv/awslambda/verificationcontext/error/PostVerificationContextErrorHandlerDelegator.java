package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import uk.co.mruoc.idv.awslambda.ErrorHandlerDelegator;
import uk.co.mruoc.idv.awslambda.InternalServerErrorHandler;

import java.util.Collections;

public class PostVerificationContextErrorHandlerDelegator extends ErrorHandlerDelegator {

    public PostVerificationContextErrorHandlerDelegator() {
        super(new InternalServerErrorHandler(), Collections.singleton(new InvalidVerificationContextRequestErrorHandler()));
    }

}
