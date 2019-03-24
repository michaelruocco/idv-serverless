package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;

import java.util.Collection;

public interface EligibleMethodsService {

    Collection<VerificationMethodSequence> loadEligibleMethods(final EligibleMethodsRequest request);

    class EligibilityHandlerNotFoundException extends RuntimeException {

        private static final String MESSAGE_FORMAT = "eligibility handler for channel %s and method %s not found";

        private final String channelId;
        private final String methodName;

        public EligibilityHandlerNotFoundException(final String channelId, final String methodName) {
            super(String.format(MESSAGE_FORMAT, channelId, methodName));
            this.channelId = channelId;
            this.methodName = methodName;
        }

        public String getChannelId() {
            return channelId;
        }

        public String getMethodName() {
            return methodName;
        }

    }

}
