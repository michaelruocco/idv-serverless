package uk.co.mruoc.idv.core.verificationcontext.service;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.MethodSequencesRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DefaultVerificationMethodsService implements VerificationMethodsService {

    private final Collection<EligibilityHandler> handlers;
    private final VerificationMethodsRequestConverter requestConverter;

    public DefaultVerificationMethodsService(final Collection<EligibilityHandler> handlers, final VerificationMethodsRequestConverter requestConverter) {
        this.handlers = handlers;
        this.requestConverter = requestConverter;
    }

    @Override
    public Collection<VerificationMethodSequence> loadMethodSequences(final MethodSequencesRequest request) {
        log.info("loading methods sequences with request {}", request);
        final VerificationPolicy policy = request.getPolicy();
        final Collection<VerificationSequencePolicy> entries = policy.getEntries();
        final Collection<VerificationMethodSequence> methods = entries.stream()
                .map(entry -> loadMethodsIfEligible(request, entry))
                .collect(Collectors.toList());
        log.info("loaded method sequences {}", methods);
        return methods;
    }

    private VerificationMethodSequence loadMethodsIfEligible(final MethodSequencesRequest request, final VerificationSequencePolicy entry) {
        final List<VerificationMethod> methods = new ArrayList<>();
        for (final VerificationMethodPolicy methodPolicy : entry.getMethods()) {
            final VerificationMethodRequest methodRequest = requestConverter.toMethodRequest(request, methodPolicy);
            final EligibilityHandler handler = getHandler(methodRequest);
            handler.loadMethodIfEligible(methodRequest).ifPresent(methods::add);
        }
        return new VerificationMethodSequence(entry.getName(), methods);
    }

    private EligibilityHandler getHandler(final VerificationMethodRequest request) {
        final String channelId = request.getChannelId();
        final String methodName = request.getMethodName();
        return handlers.stream()
                .filter(handler -> handler.isSupported(request))
                .findFirst()
                .orElseThrow(() -> new EligibilityHandlerNotFoundException(channelId, methodName));
    }

}