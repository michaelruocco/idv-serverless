package uk.co.mruoc.idv.awslambda.verificationcontext.error;

import uk.co.mruoc.idv.awslambda.ErrorHandlerDelegator;
import uk.co.mruoc.idv.awslambda.InternalServerErrorHandler;

import java.util.Arrays;

public class PostVerificationContextErrorHandlerDelegator extends ErrorHandlerDelegator {

    public PostVerificationContextErrorHandlerDelegator() {
        super(new InternalServerErrorHandler(), Arrays.asList(
                new InvalidVerificationContextRequestErrorHandler(),
                new VerificationPolicyNotConfiguredForChannelErrorHandler(),
                new VerificationPolicyNotConfiguredForActivityErrorHandler()));
    }

}
