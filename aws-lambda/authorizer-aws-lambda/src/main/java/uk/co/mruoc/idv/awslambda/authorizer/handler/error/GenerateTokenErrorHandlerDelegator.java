package uk.co.mruoc.idv.awslambda.authorizer.handler.error;

import uk.co.mruoc.idv.awslambda.ErrorHandlerDelegator;
import uk.co.mruoc.idv.awslambda.InternalServerErrorHandler;

import java.util.Collections;

public class GenerateTokenErrorHandlerDelegator extends ErrorHandlerDelegator {

    public GenerateTokenErrorHandlerDelegator() {
        super(new InternalServerErrorHandler(), Collections.emptyList());
    }

}
