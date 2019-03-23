package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.service.FixedExpiryCalculator;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextRequestConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextService;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility.UkEligibleMethodsService;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.UkVerificationPoliciesService;

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
        return VerificationContextService.builder()
                .requestConverter(new VerificationContextRequestConverter())
                .identityService(identityService)
                .policiesService(new UkVerificationPoliciesService())
                .timeService(new DefaultTimeService())
                .eligibleMethodsService(new UkEligibleMethodsService())
                .expiryCalculator(new FixedExpiryCalculator())
                .idGenerator(new RandomUuidGenerator())
                .dao(dao)
                .build();
    }

}
