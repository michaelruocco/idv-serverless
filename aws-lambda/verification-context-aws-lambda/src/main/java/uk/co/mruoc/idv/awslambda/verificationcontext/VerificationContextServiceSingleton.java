package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibleMethodsRequestConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibleMethodsService;
import uk.co.mruoc.idv.core.verificationcontext.service.FixedExpiryCalculator;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextRequestConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextService;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationPoliciesService;
import uk.co.mruoc.idv.plugin.verificationcontext.eligibility.FakeCardCredentialsEligibilityHandler;
import uk.co.mruoc.idv.plugin.verificationcontext.eligibility.FakeMobilePinsentryEligibilityHandler;
import uk.co.mruoc.idv.plugin.verificationcontext.eligibility.FakeOtpSmsEligibilityHandler;
import uk.co.mruoc.idv.plugin.verificationcontext.eligibility.FakePhysicalPinsentryEligibilityHandler;
import uk.co.mruoc.idv.plugin.verificationcontext.eligibility.FakePushNotificationEligibilityHandler;

import java.util.Arrays;
import java.util.Collection;

public class VerificationContextServiceSingleton {

    private static VerificationContextService SERVICE;

    private VerificationContextServiceSingleton() {
        // utility class
    }

    public static VerificationContextService get(final IdentityService identityService, final VerificationContextDao dao) {
        if (SERVICE == null) {
            SERVICE = buildService(identityService, dao);
        }
        return SERVICE;
    }

    private static VerificationContextService buildService(final IdentityService identityService, final VerificationContextDao dao) {
        final EligibleMethodsService eligibleMethodsService = buildEligibleMethodsService();
        return VerificationContextService.builder()
                .requestConverter(new VerificationContextRequestConverter())
                .identityService(identityService)
                .policiesService(new VerificationPoliciesService())
                .timeService(new DefaultTimeService())
                .eligibleMethodsService(eligibleMethodsService)
                .expiryCalculator(new FixedExpiryCalculator())
                .idGenerator(new RandomUuidGenerator())
                .dao(dao)
                .build();
    }

    private static EligibleMethodsService buildEligibleMethodsService() {
        final Collection<EligibilityHandler> handlers = Arrays.asList(
                new FakePushNotificationEligibilityHandler(),
                new FakePhysicalPinsentryEligibilityHandler(),
                new FakeMobilePinsentryEligibilityHandler(),
                new FakeCardCredentialsEligibilityHandler(),
                new FakeOtpSmsEligibilityHandler()
        );
        return EligibleMethodsService.builder()
                .requestConverter(new EligibleMethodsRequestConverter())
                .handlers(handlers)
                .build();
    }

}
