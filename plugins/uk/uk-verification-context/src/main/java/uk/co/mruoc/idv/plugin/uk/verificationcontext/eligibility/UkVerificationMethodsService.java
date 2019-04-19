package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.service.DefaultVerificationMethodsService;
import uk.co.mruoc.idv.core.verificationcontext.service.AvailabilityHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationMethodsRequestConverter;

import java.util.Arrays;
import java.util.Collection;

public class UkVerificationMethodsService extends DefaultVerificationMethodsService {

    private static final Collection<AvailabilityHandler> HANDLERS = Arrays.asList(
            new FakePushNotificationAvailabilityHandler(),
            new FakePhysicalPinsentryAvailabilityHandler(),
            new FakeMobilePinsentryAvailabilityHandler(),
            new FakeCardCredentialsAvailabilityHandler(),
            new FakeOtpSmsAvailabilityHandler()
    );

    public UkVerificationMethodsService() {
        super(HANDLERS, new VerificationMethodsRequestConverter());
    }

}
