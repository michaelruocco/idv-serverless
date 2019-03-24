package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import uk.co.mruoc.idv.awslambda.ErrorHandlerDelegator;
import uk.co.mruoc.idv.awslambda.InternalServerErrorHandler;
import java.util.Collections;

public class GetVerificationContextErrorHandlerDelegator extends ErrorHandlerDelegator {

    public GetVerificationContextErrorHandlerDelegator() {
        super(new InternalServerErrorHandler(), Collections.emptyList());
    }

}
