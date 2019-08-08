package uk.co.mruoc.idv.plugin.uk.verificationcontext.availability;

import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import uk.co.mruoc.idv.core.verificationcontext.service.DefaultVerificationMethodsService;
import uk.co.mruoc.idv.core.verificationcontext.service.AvailabilityHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationMethodsRequestConverter;

import java.util.Arrays;
import java.util.Collection;

public class UkVerificationMethodsService extends DefaultVerificationMethodsService {

    private static final ThreadPoolBulkheadConfig BULKHEAD_CONFIG = ThreadPoolBulkheadConfig.custom()
            .maxThreadPoolSize(5)
            .coreThreadPoolSize(5)
            .queueCapacity(5)
            .build();
    private static final ThreadPoolBulkheadRegistry REGISTRY = ThreadPoolBulkheadRegistry.of(BULKHEAD_CONFIG);
    private static final ThreadPoolBulkhead BULKHEAD = REGISTRY.bulkhead("availabilityBulkhead", BULKHEAD_CONFIG);

    private static final Collection<AvailabilityHandler> HANDLERS = Arrays.asList(
            new FakePushNotificationAvailabilityHandler(),
            new FakePhysicalPinsentryAvailabilityHandler(),
            new FakeMobilePinsentryAvailabilityHandler(),
            new FakeCardCredentialsAvailabilityHandler(),
            new FakeOtpSmsAvailabilityHandler()
    );

    public UkVerificationMethodsService() {
        super(BULKHEAD, HANDLERS, new VerificationMethodsRequestConverter());
    }

}
