package uk.co.mruoc.idv.awslambda.lockoutdecision;

import uk.co.mruoc.idv.awslambda.ErrorHandlerDelegator;
import uk.co.mruoc.idv.awslambda.InternalServerErrorHandler;

import java.util.Arrays;
import java.util.Collections;

public class PutResetLockoutStateErrorHandlerDelegator extends ErrorHandlerDelegator {

    public PutResetLockoutStateErrorHandlerDelegator() {
        super(new InternalServerErrorHandler(), Collections.emptyList());
    }

}
