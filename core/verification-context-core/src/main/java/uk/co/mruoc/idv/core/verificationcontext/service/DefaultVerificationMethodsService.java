package uk.co.mruoc.idv.core.verificationcontext.service;

import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
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
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Slf4j
public class DefaultVerificationMethodsService implements VerificationMethodsService {

    private final ThreadPoolBulkhead bulkhead;
    private final Collection<EligibilityHandler> handlers;
    private final VerificationMethodsRequestConverter requestConverter;

    public DefaultVerificationMethodsService(final ThreadPoolBulkhead bulkhead,
                                             final Collection<EligibilityHandler> handlers,
                                             final VerificationMethodsRequestConverter requestConverter) {
        this.bulkhead = bulkhead;
        this.handlers = handlers;
        this.requestConverter = requestConverter;
    }

    @Override
    public Collection<VerificationMethodSequence> loadMethodSequences(final MethodSequencesRequest request) {
        log.info("loading method sequences with request {}", request);
        final VerificationPolicy policy = request.getPolicy();
        final Collection<VerificationSequencePolicy> sequencePolicies = policy.getSequencePolicies();
        final Collection<VerificationMethodSequenceFuture> methodSequenceFutures = sequencePolicies.stream()
                .map(sequencePolicy -> loadMethodSequences(request, sequencePolicy))
                .collect(Collectors.toList());
        log.info("loaded method sequences futures {}", methodSequenceFutures);
        final Collection<VerificationMethodSequence> sequences = methodSequenceFutures.stream()
                .map(DefaultVerificationMethodsService::toSequence)
                .collect(Collectors.toList());
        log.info("loaded sequences {}", sequences);
        return sequences;
    }

    private VerificationMethodSequenceFuture loadMethodSequences(final MethodSequencesRequest request, final VerificationSequencePolicy sequencePolicy) {
        final Collection<CompletionStage<VerificationMethod>> methodStages = new ArrayList<>();
        for (final VerificationMethodPolicy methodPolicy : sequencePolicy.getMethods()) {
            final VerificationMethodRequest methodRequest = requestConverter.toMethodRequest(request, methodPolicy);
            final EligibilityHandler handler = getHandler(methodRequest);
            final VerificationMethodSupplier supplier = new VerificationMethodSupplier(methodRequest, handler);
            methodStages.add(bulkhead.executeSupplier(supplier));
        }
        return new VerificationMethodSequenceFuture(sequencePolicy.getName(), methodStages);
    }

    private EligibilityHandler getHandler(final VerificationMethodRequest request) {
        final String channelId = request.getChannelId();
        final String methodName = request.getMethodName();
        return handlers.stream()
                .filter(handler -> handler.isSupported(request))
                .findFirst()
                .orElseThrow(() -> new AvailabilityHandlerNotFoundException(channelId, methodName));
    }

    private static VerificationMethodSequence toSequence(final VerificationMethodSequenceFuture future) {
        return new VerificationMethodSequence(future.getName(), future.getMethods());
    }



}