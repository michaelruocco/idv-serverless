package uk.co.mruoc.idv.core.verificationcontext.service;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
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
public class EligibleMethodsService {

    private Collection<EligibilityHandler> handlers;

    public EligibleMethodsService(final Collection<EligibilityHandler> handlers) {
        this.handlers = handlers;
    }

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
            final EligibilityHandler handler = getHandler(request.getChannel(), methodPolicy.getMethodName());
            handler.loadMethodIfEligible(request, methodPolicy).ifPresent(methods::add);
        }
        return new VerificationMethodSequence(entry.getName(), methods);
    }

    private EligibilityHandler getHandler(final Channel channel, final String methodName) {
        final String channelId = channel.getId();
        return handlers.stream()
                .filter(handler -> handler.isSupported(channelId, methodName))
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