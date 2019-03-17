package uk.co.mruoc.idv.awslambda.identity;

import uk.co.mruoc.idv.awslambda.ErrorHandlerDelegator;
import uk.co.mruoc.idv.awslambda.InternalServerErrorHandler;

import java.util.Arrays;

public class GetIdentityErrorHandlerDelegator extends ErrorHandlerDelegator {

    public GetIdentityErrorHandlerDelegator() {
        super(new InternalServerErrorHandler(), Arrays.asList(
                new IdentityNotFoundErrorHandler(),
                new InvalidIdentityRequestErrorHandler(),
                new InvalidIdvIdErrorHandler()));
    }

}
