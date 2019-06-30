package uk.co.mruoc.idv.awslambda.lockoutdecision;

import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.IdvIdGenerator;
import uk.co.mruoc.idv.core.lockoutstate.UpdateLockoutStateService;
import uk.co.mruoc.idv.core.lockoutstate.service.LoadLockoutStateService;
import uk.co.mruoc.idv.core.lockoutstate.service.LockoutStateCalculationService;
import uk.co.mruoc.idv.core.lockoutstate.service.VerificationAttemptsConverter;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationattempts.service.VerificationAttemptsService;

public class UpdateLockoutStateServiceFactory {

    private final VerificationAttemptsDao attemptsDao;
    private final IdentityDao identityDao;

    public UpdateLockoutStateServiceFactory(final VerificationAttemptsDao attemptsDao, final IdentityDao identityDao) {
        this.attemptsDao = attemptsDao;
        this.identityDao = identityDao;
    }

    public UpdateLockoutStateService build() {
        final TimeService timeService = new DefaultTimeService();
        final VerificationAttemptsService attemptsService = buildLoadAttemptsService();
        final LoadLockoutStateServiceFactory factory = new LoadLockoutStateServiceFactory(attemptsDao, identityDao);
        final LoadLockoutStateService loadLockoutStateService = factory.build();
        return UpdateLockoutStateService.builder()
                .loadLockoutStateService(loadLockoutStateService)
                .policiesService(new StubbedLockoutPoliciesService())
                .calculationService(new LockoutStateCalculationService(new VerificationAttemptsConverter(timeService)))
                .attemptsService(attemptsService)
                .build();
    }

    private VerificationAttemptsService buildLoadAttemptsService() {
        final IdentityService identityService = buildIdentityService();
        return VerificationAttemptsService.builder()
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
