package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.service.DefaultVerificationMethodsService;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationMethodsRequestConverter;

import java.util.Arrays;
import java.util.Collection;

public class UkVerificationMethodsService extends DefaultVerificationMethodsService {

    private static final Collection<EligibilityHandler> HANDLERS = Arrays.asList(
            new FakePushNotificationEligibilityHandler(),
            new FakePhysicalPinsentryEligibilityHandler(),
            new FakeMobilePinsentryEligibilityHandler(),
            new FakeCardCredentialsEligibilityHandler(),
            new FakeOtpSmsEligibilityHandler()
    );

    public UkVerificationMethodsService() {
        super(HANDLERS, new VerificationMethodsRequestConverter());
    }

}
