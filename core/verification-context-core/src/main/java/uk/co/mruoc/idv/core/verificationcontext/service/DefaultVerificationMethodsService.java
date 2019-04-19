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

    private final Collection<AvailabilityHandler> handlers;
    private final VerificationMethodsRequestConverter requestConverter;

    public DefaultVerificationMethodsService(final Collection<AvailabilityHandler> handlers, final VerificationMethodsRequestConverter requestConverter) {
        this.handlers = handlers;
        this.requestConverter = requestConverter;
    }

    @Override
    public Collection<VerificationMethodSequence> loadMethodSequences(final MethodSequencesRequest request) {
        log.info("loading method sequences with request {}", request);
        final VerificationPolicy policy = request.getPolicy();
        final Collection<VerificationSequencePolicy> sequencePolicies = policy.getSequencePolicies();
        final Collection<VerificationMethodSequence> methods = sequencePolicies.stream()
                .map(sequencePolicy -> loadMethodSequences(request, sequencePolicy))
                .collect(Collectors.toList());
        log.info("loaded method sequences {}", methods);
        return methods;
    }

    private VerificationMethodSequence loadMethodSequences(final MethodSequencesRequest request, final VerificationSequencePolicy sequencePolicy) {
        final List<VerificationMethod> methods = new ArrayList<>();
        for (final VerificationMethodPolicy methodPolicy : sequencePolicy.getMethods()) {
            final VerificationMethodRequest methodRequest = requestConverter.toMethodRequest(request, methodPolicy);
            final AvailabilityHandler handler = getHandler(methodRequest);
            methods.add(handler.loadMethod(methodRequest));
        }
        return new VerificationMethodSequence(sequencePolicy.getName(), methods, sequencePolicy.getFailureStrategy());
    }

    private AvailabilityHandler getHandler(final VerificationMethodRequest request) {
        final String channelId = request.getChannelId();
        final String methodName = request.getMethodName();
        return handlers.stream()
                .filter(handler -> handler.isSupported(request))
                .findFirst()
                .orElseThrow(() -> new AvailabilityHandlerNotFoundException(channelId, methodName));
    }

}