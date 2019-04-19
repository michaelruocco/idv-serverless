package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.verificationcontext.model.MethodSequencesRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;

import java.util.Collection;

public interface VerificationMethodsService {

    Collection<VerificationMethodSequence> loadMethodSequences(final MethodSequencesRequest request);

    class AvailabilityHandlerNotFoundException extends RuntimeException {

        private static final String MESSAGE_FORMAT = "availability handler for channel %s and method %s not found";

        private final String channelId;
        private final String methodName;

        public AvailabilityHandlerNotFoundException(final String channelId, final String methodName) {
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
