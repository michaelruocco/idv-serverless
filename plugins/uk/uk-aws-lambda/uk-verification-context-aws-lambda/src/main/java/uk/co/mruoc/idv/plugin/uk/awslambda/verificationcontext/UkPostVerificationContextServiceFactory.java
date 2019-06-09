package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.verificationcontext.CreateVerificationContextServiceFactory;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDaoFactory;
import uk.co.mruoc.idv.core.lockoutdecision.service.LoadVerificationAttemptsService;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutPoliciesService;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutStateService;
import uk.co.mruoc.idv.core.lockoutdecision.service.VerificationAttemptsConverter;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.verificationcontext.service.FixedExpiryCalculator;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDaoFactory;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextRequestConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.CreateVerificationContextService;
import uk.co.mruoc.idv.dao.lockoutdecision.DynamoVerificationAttemptsDaoFactory;
import uk.co.mruoc.idv.dao.verificationcontext.DynamoVerificationContextDaoFactory;
import uk.co.mruoc.idv.events.EventPublisher;
import uk.co.mruoc.idv.events.sns.SnsEventPublisherFactory;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextJsonConverterFactory;
import uk.co.mruoc.idv.plugin.uk.awslambda.identity.UkIdentityServiceFactory;
import uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa.UkLockoutPoliciesService;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility.UkVerificationMethodsService;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.UkVerificationPoliciesService;

@Slf4j
public class UkPostVerificationContextServiceFactory implements CreateVerificationContextServiceFactory {

    private static final JsonConverter JSON_CONVERTER = new VerificationContextJsonConverterFactory().build();
    private static final Environment ENVIRONMENT = new Environment();

    private static CreateVerificationContextService CONTEXT_SERVICE;

    private final IdentityService identityService;
    private final VerificationContextDao contextDao;
    private final EventPublisher eventPublisher;
    private final VerificationAttemptsDao attemptsDao;
    private final LockoutPoliciesService lockoutPoliciesService;

    public UkPostVerificationContextServiceFactory() {
        this(new UkIdentityServiceFactory().build(),
                buildContextDao(),
                buildEventPublisher(),
                buildAttemptsDao(),
                new UkLockoutPoliciesService());
    }

    public UkPostVerificationContextServiceFactory(final IdentityService identityService,
                                                   final VerificationContextDao contextDao,
                                                   final EventPublisher eventPublisher,
                                                   final VerificationAttemptsDao attemptsDao,
                                                   final LockoutPoliciesService lockoutPoliciesService) {
        this.identityService = identityService;
        this.contextDao = contextDao;
        this.eventPublisher = eventPublisher;
        this.attemptsDao = attemptsDao;
        this.lockoutPoliciesService = lockoutPoliciesService;
    }

    @Override
    public CreateVerificationContextService build() {
        if (CONTEXT_SERVICE == null) {
            CONTEXT_SERVICE = buildCreateContextService();
        }
        return CONTEXT_SERVICE;
    }

    private CreateVerificationContextService buildCreateContextService() {
        final TimeService timeService = new DefaultTimeService();
        final LockoutStateService lockoutStateService = buildLockoutStateService();
        return CreateVerificationContextService.builder()
                .requestConverter(new VerificationContextRequestConverter())
                .identityService(identityService)
                .policiesService(new UkVerificationPoliciesService())
                .timeService(timeService)
                .verificationMethodsService(new UkVerificationMethodsService())
                .expiryCalculator(new FixedExpiryCalculator())
                .idGenerator(new RandomUuidGenerator())
                .dao(contextDao)
                .contextConverter(new VerificationContextConverter(timeService))
                .eventPublisher(eventPublisher)
                .lockoutStateService(lockoutStateService)
                .build();
    }

    private static EventPublisher buildEventPublisher() {
        final SnsEventPublisherFactory factory = new SnsEventPublisherFactory(ENVIRONMENT, JSON_CONVERTER);
        return factory.build();
    }

    private LockoutStateService buildLockoutStateService() {
        final TimeService timeService = new DefaultTimeService();
        return LockoutStateService.builder()
                .converter(new VerificationAttemptsConverter(timeService))
                .dao(attemptsDao)
                .loadAttemptsService(buildLoadAttemptsService())
                .policiesService(lockoutPoliciesService)
                .build();
    }

    private LoadVerificationAttemptsService buildLoadAttemptsService() {
        return LoadVerificationAttemptsService.builder()
                .dao(attemptsDao)
                .uuidGenerator(new RandomUuidGenerator())
                .identityService(identityService)
                .build();
    }

    private static VerificationContextDao buildContextDao() {
        final VerificationContextDaoFactory factory = new DynamoVerificationContextDaoFactory(ENVIRONMENT, JSON_CONVERTER);
        return factory.build();
    }

    private static VerificationAttemptsDao buildAttemptsDao() {
        final VerificationAttemptsDaoFactory factory = new DynamoVerificationAttemptsDaoFactory(ENVIRONMENT, JSON_CONVERTER);
        return factory.build();
    }

}
