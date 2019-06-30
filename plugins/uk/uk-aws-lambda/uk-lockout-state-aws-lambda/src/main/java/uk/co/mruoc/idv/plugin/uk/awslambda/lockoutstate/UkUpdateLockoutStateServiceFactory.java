package uk.co.mruoc.idv.plugin.uk.awslambda.lockoutstate;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.lockoutdecision.UpdateLockoutStateServiceFactory;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.lockoutstate.UpdateLockoutStateService;
import uk.co.mruoc.idv.core.lockoutstate.service.LockoutPoliciesService;
import uk.co.mruoc.idv.core.lockoutstate.service.LockoutStateCalculationService;
import uk.co.mruoc.idv.core.lockoutstate.service.VerificationAttemptConverter;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDaoFactory;
import uk.co.mruoc.idv.core.verificationattempts.service.VerificationAttemptsService;
import uk.co.mruoc.idv.core.lockoutstate.service.LoadLockoutStateService;
import uk.co.mruoc.idv.core.lockoutstate.service.VerificationAttemptsConverter;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService;
import uk.co.mruoc.idv.dao.verificationattempts.DynamoVerificationAttemptsDaoFactory;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.verificationattempts.VerificationAttemptsJsonConverterFactory;
import uk.co.mruoc.idv.plugin.uk.awslambda.identity.UkIdentityServiceFactory;
import uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext.UkGetVerificationContextServiceFactory;
import uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa.UkLockoutPoliciesService;

@Slf4j
public class UkUpdateLockoutStateServiceFactory implements UpdateLockoutStateServiceFactory {

    private static final JsonConverter JSON_CONVERTER = new VerificationAttemptsJsonConverterFactory().build();
    private static final Environment ENVIRONMENT = new Environment();

    private final GetVerificationContextService getContextService;
    private final IdentityService identityService;
    private final VerificationAttemptsDao attemptsDao;

    public UkUpdateLockoutStateServiceFactory() {
        this(new UkGetVerificationContextServiceFactory().build(),
                new UkIdentityServiceFactory().build(),
                buildAttemptsDao());
    }

    public UkUpdateLockoutStateServiceFactory(final GetVerificationContextService getContextService,
                                              final IdentityService identityService,
                                              final VerificationAttemptsDao attemptsDao) {
        this.getContextService = getContextService;
        this.identityService = identityService;
        this.attemptsDao = attemptsDao;
    }

    @Override
    public UpdateLockoutStateService build() {
        return buildService();
    }

    /*private final VerificationAttemptsService attemptsService;
    private final LoadLockoutStateService loadLockoutStateService;
    private final LockoutStateCalculationService calculationService;
    private final VerificationAttemptConverter attemptConverter;
    private final LockoutPoliciesService policiesService;
    private final GetVerificationContextService getContextService;*/

    private UpdateLockoutStateService buildService() {
        final VerificationAttemptsService attemptsService = VerificationAttemptsService.builder()
                .identityService(identityService)
                .dao(attemptsDao)
                .uuidGenerator(new RandomUuidGenerator())
                .build();
        final VerificationAttemptsConverter attemptsConverter = new VerificationAttemptsConverter(new DefaultTimeService());
        final LockoutStateCalculationService calculationService = new LockoutStateCalculationService(attemptsConverter);
        final LockoutPoliciesService policiesService = new UkLockoutPoliciesService();
        final LoadLockoutStateService loadLockoutStateService = LoadLockoutStateService.builder()
                .attemptsService(buildLoadAttemptsService())
                .calculationService(calculationService)
                .policiesService(policiesService)
                .build();
        return UpdateLockoutStateService.builder()
                .attemptsService(attemptsService)
                .calculationService(calculationService)
                .policiesService(policiesService)
                .loadLockoutStateService(loadLockoutStateService)
                .attemptConverter(new VerificationAttemptConverter())
                .getContextService(getContextService)
                .build();
    }

    private VerificationAttemptsService buildLoadAttemptsService() {
        return VerificationAttemptsService.builder()
                .uuidGenerator(new RandomUuidGenerator())
                .identityService(identityService)
                .dao(attemptsDao)
                .build();
    }

    private static VerificationAttemptsDao buildAttemptsDao() {
        final VerificationAttemptsDaoFactory factory = new DynamoVerificationAttemptsDaoFactory(ENVIRONMENT, JSON_CONVERTER);
        return factory.build();
    }

}
