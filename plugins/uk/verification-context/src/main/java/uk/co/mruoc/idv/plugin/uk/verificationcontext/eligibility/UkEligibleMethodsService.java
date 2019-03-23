package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibleMethodsRequestConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibleMethodsService;

import java.util.Arrays;
import java.util.Collection;

public class UkEligibleMethodsService extends EligibleMethodsService {

    private static final Collection<EligibilityHandler> HANDLERS = Arrays.asList(
            new FakePushNotificationEligibilityHandler(),
            new FakePhysicalPinsentryEligibilityHandler(),
            new FakeMobilePinsentryEligibilityHandler(),
            new FakeCardCredentialsEligibilityHandler(),
            new FakeOtpSmsEligibilityHandler()
    );

    public UkEligibleMethodsService() {
        super(HANDLERS, new EligibleMethodsRequestConverter());
    }

}
