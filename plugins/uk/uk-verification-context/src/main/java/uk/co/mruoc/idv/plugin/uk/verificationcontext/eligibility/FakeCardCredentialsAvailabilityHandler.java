package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.CardCredentialsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationStatus;
import uk.co.mruoc.idv.core.verificationcontext.service.AvailabilityHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class FakeCardCredentialsAvailabilityHandler implements AvailabilityHandler {

    private static final ThreadPoolBulkheadConfig BULKHEAD_CONFIG = ThreadPoolBulkheadConfig.custom()
            .maxThreadPoolSize(10)
            .coreThreadPoolSize(2)
            .queueCapacity(20)
            .build();
    private static final ThreadPoolBulkheadRegistry REGISTRY = ThreadPoolBulkheadRegistry.of(BULKHEAD_CONFIG);
    private static final ThreadPoolBulkhead BULKHEAD = REGISTRY.bulkhead("cardCredentialsBulkhead", BULKHEAD_CONFIG);

    private static final String METHOD_NAME = VerificationMethod.Names.CARD_CREDENTIALS;

    @Override
    public CompletableFuture<VerificationMethod> loadMethod(final VerificationMethodRequest request) {
        final CompletionStage<VerificationMethod> stage = BULKHEAD.executeSupplier(new VerificationMethodSupplier(request));
        return stage.toCompletableFuture();
    }

    @Override
    public boolean isSupported(final VerificationMethodRequest request) {
        return METHOD_NAME.equals(request.getMethodName());
    }

    @Slf4j
    private static class VerificationMethodSupplier implements Supplier<VerificationMethod> {

        private final VerificationMethodRequest request;

        public VerificationMethodSupplier(final VerificationMethodRequest request) {
            this.request = request;
        }

        @Override
        public VerificationMethod get() {
            try {
                TimeUnit.MILLISECONDS.sleep(150);
                return new CardCredentialsVerificationMethod(request.getDuration());
            } catch (final InterruptedException e) {
                log.error("interrupted exception", e);
                return new CardCredentialsVerificationMethod(request.getDuration(), VerificationStatus.UNAVAILABLE);
            }
        }

    }

}
