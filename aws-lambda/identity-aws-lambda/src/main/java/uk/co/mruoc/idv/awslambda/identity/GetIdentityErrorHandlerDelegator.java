package uk.co.mruoc.idv.awslambda.identity;

import uk.co.mruoc.idv.awslambda.ErrorHandlerDelegator;
import uk.co.mruoc.idv.awslambda.InternalServerErrorHandler;

import java.util.Collections;

public class GetIdentityErrorHandlerDelegator extends ErrorHandlerDelegator {

    public GetIdentityErrorHandlerDelegator() {
        super(new InternalServerErrorHandler(), Collections.singleton(new IdentityNotFoundErrorHandler()));
    }

}