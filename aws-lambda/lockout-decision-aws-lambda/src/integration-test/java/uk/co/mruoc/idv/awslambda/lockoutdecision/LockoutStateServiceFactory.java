package uk.co.mruoc.idv.awslambda.lockoutdecision;

import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.IdvIdGenerator;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.service.LoadVerificationAttemptsService;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutStateService;
import uk.co.mruoc.idv.core.lockoutdecision.service.VerificationAttemptsConverter;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.dao.lockoutdecision.FakeVerificationAttemptsDao;

public class LockoutStateServiceFactory {

    private final VerificationAttemptsDao attemptsDao;
    private final IdentityDao identityDao;

    public LockoutStateServiceFactory(final VerificationAttemptsDao attemptsDao, final IdentityDao identityDao) {
        this.attemptsDao = attemptsDao;
        this.identityDao = identityDao;
    }

    public LockoutStateService build() {
        final TimeService timeService = new DefaultTimeService();
        final LoadVerificationAttemptsService attemptsService = buildLoadAttemptsService();
        return LockoutStateService.builder()
                .dao(new FakeVerificationAttemptsDao())
                .policiesService(new StubbedLockoutPoliciesService())
                .converter(new VerificationAttemptsConverter(timeService))
                .loadAttemptsService(attemptsService)
                .build();
    }

    private LoadVerificationAttemptsService buildLoadAttemptsService() {
        final IdentityService identityService = buildIdentityService();
        return LoadVerificationAttemptsService.builder()
                .identityService(identityService)
                .uuidGenerator(new RandomUuidGenerator())
                .dao(attemptsDao)
                .build();
    }

    private IdentityService buildIdentityService() {
        return IdentityService.builder()
                .dao(identityDao)
                .idvIdGenerator(new IdvIdGenerator())
                .build();
    }

}
