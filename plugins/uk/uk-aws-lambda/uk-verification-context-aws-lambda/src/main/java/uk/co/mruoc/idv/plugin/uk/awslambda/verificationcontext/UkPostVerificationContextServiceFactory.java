package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.identity.IdentityServiceFactory;
import uk.co.mruoc.idv.awslambda.verificationcontext.CreateVerificationContextServiceFactory;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.verificationcontext.service.FixedExpiryCalculator;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDaoFactory;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextRequestConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.CreateVerificationContextService;
import uk.co.mruoc.idv.dao.verificationcontext.DynamoVerificationContextDaoFactory;
import uk.co.mruoc.idv.events.EventPublisher;
import uk.co.mruoc.idv.events.sns.SnsEventPublisherFactory;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextJsonConverterFactory;
import uk.co.mruoc.idv.plugin.uk.awslambda.identity.UkIdentityServiceFactory;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility.UkVerificationMethodsService;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.UkVerificationPoliciesService;

@Slf4j
public class UkPostVerificationContextServiceFactory implements CreateVerificationContextServiceFactory {

    private static final JsonConverter JSON_CONVERTER = new VerificationContextJsonConverterFactory().build();
    private static final Environment ENVIRONMENT = new Environment();

    private static CreateVerificationContextService CONTEXT_SERVICE;

    private final IdentityServiceFactory identityServiceFactory;
    private final VerificationContextDao dao;
    private final EventPublisher eventPublisher;

    public UkPostVerificationContextServiceFactory() {
        this(new UkIdentityServiceFactory(), buildDao(), buildEventPublisher());
    }

    public UkPostVerificationContextServiceFactory(final IdentityServiceFactory identityServiceFactory,
                                                   final VerificationContextDao dao,
                                                   final EventPublisher eventPublisher) {
        this.identityServiceFactory = identityServiceFactory;
        this.dao = dao;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public CreateVerificationContextService build() {
        if (CONTEXT_SERVICE == null) {
            CONTEXT_SERVICE = buildService();
        }
        return CONTEXT_SERVICE;
    }

    private CreateVerificationContextService buildService() {
        final TimeService timeService = new DefaultTimeService();
        return CreateVerificationContextService.builder()
                .requestConverter(new VerificationContextRequestConverter())
                .identityService(identityServiceFactory.getIdentityService())
                .policiesService(new UkVerificationPoliciesService())
                .timeService(timeService)
                .verificationMethodsService(new UkVerificationMethodsService())
                .expiryCalculator(new FixedExpiryCalculator())
                .idGenerator(new RandomUuidGenerator())
                .dao(dao)
                .contextConverter(new VerificationContextConverter(timeService))
                .eventPublisher(eventPublisher)
                .build();
    }

    private static EventPublisher buildEventPublisher() {
        final SnsEventPublisherFactory factory = new SnsEventPublisherFactory(ENVIRONMENT, JSON_CONVERTER);
        return factory.build();
    }

    private static VerificationContextDao buildDao() {
        final VerificationContextDaoFactory factory = new DynamoVerificationContextDaoFactory(ENVIRONMENT, JSON_CONVERTER);
        return factory.build();
    }

}
