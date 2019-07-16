package uk.co.mruoc.idv.core.verificationcontext.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
        final Collection<CompletableFuture<VerificationMethod>> methodFutures = new ArrayList<>();
        for (final VerificationMethodPolicy methodPolicy : sequencePolicy.getMethods()) {
            final VerificationMethodRequest methodRequest = requestConverter.toMethodRequest(request, methodPolicy);
            final AvailabilityHandler handler = getHandler(methodRequest);
            methodFutures.add(handler.loadMethod(methodRequest));
        }
        return new VerificationMethodSequenceFuture(sequencePolicy.getName(), methodFutures);
    }

    private AvailabilityHandler getHandler(final VerificationMethodRequest request) {
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

    @RequiredArgsConstructor
    @Getter
    @Slf4j
    private static class VerificationMethodSequenceFuture {

        private final String name;
        private final Collection<CompletableFuture<VerificationMethod>> methodFutures;

        public Collection<VerificationMethod> getMethods() {
            final Collection<VerificationMethod> methods = new ArrayList<>();
            for (final CompletableFuture<VerificationMethod> methodFuture : methodFutures) {
                try {
                    methods.add(methodFuture.get());
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("interrupted exception", e);
                    return Collections.emptyList();
                } catch (final ExecutionException e) {
                    log.error("execution exception", e);
                    return Collections.emptyList();
                }
            }
            return methods;
        }
    }

}