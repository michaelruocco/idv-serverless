package uk.co.mruoc.idv.core.verificationcontext.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Builder
public class EligibleMethodsService {

    private final Collection<EligibilityHandler> handlers;
    private final EligibleMethodsRequestConverter requestConverter;

    public Collection<VerificationMethodSequence> loadEligibleMethods(final EligibleMethodsRequest request) {
        log.info("loading eligible methods with request {}", request);
        final VerificationPolicy policy = request.getPolicy();
        final Collection<VerificationMethodPolicyEntry> entries = policy.getEntries();
        final Collection<VerificationMethodSequence> methods = entries.stream()
                .map(entry -> loadMethodsIfEligible(request, entry))
                .collect(Collectors.toList());
        log.info("loaded eligible methods {}", methods);
        return methods;
    }

    private VerificationMethodSequence loadMethodsIfEligible(final EligibleMethodsRequest request, final VerificationMethodPolicyEntry entry) {
        final List<VerificationMethod> methods = new ArrayList<>();
        for (final VerificationMethodPolicy methodPolicy : entry.getMethods()) {
            final EligibleMethodRequest methodRequest = requestConverter.toMethodRequest(request, methodPolicy);
            final EligibilityHandler handler = getHandler(methodRequest);
            handler.loadMethodIfEligible(methodRequest).ifPresent(methods::add);
        }
        return new VerificationMethodSequence(entry.getName(), methods);
    }

    private EligibilityHandler getHandler(final EligibleMethodRequest request) {
        final String channelId = request.getChannelId();
        final String methodName = request.getMethodName();
        return handlers.stream()
                .filter(handler -> handler.isSupported(request))
                .findFirst()
                .orElseThrow(() -> new EligibilityHandlerNotFoundException(channelId, methodName));
    }

    public static class EligibilityHandlerNotFoundException extends RuntimeException {

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