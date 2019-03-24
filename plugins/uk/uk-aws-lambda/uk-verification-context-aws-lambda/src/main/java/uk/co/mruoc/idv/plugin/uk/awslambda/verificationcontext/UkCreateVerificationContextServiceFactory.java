package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.identity.IdentityServiceFactory;
import uk.co.mruoc.idv.awslambda.verificationcontext.CreateVerificationContextServiceFactory;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.service.FixedExpiryCalculator;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDaoFactory;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextRequestConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.CreateVerificationContextService;
import uk.co.mruoc.idv.dao.verificationcontext.DynamoVerificationContextDaoFactory;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextObjectMapperSingleton;
import uk.co.mruoc.idv.plugin.uk.awslambda.identity.UkIdentityServiceFactory;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility.UkEligibleMethodsService;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.UkVerificationPoliciesService;

public class UkCreateVerificationContextServiceFactory implements CreateVerificationContextServiceFactory {

    private static CreateVerificationContextService CONTEXT_SERVICE;

    private final IdentityServiceFactory identityServiceFactory;
    private final VerificationContextDao dao;

    public UkCreateVerificationContextServiceFactory() {
        this(new UkIdentityServiceFactory(), buildDao());
    }

    public UkCreateVerificationContextServiceFactory(final IdentityServiceFactory identityServiceFactory, final VerificationContextDao dao) {
        this.identityServiceFactory = identityServiceFactory;
        this.dao = dao;
    }

    @Override
    public CreateVerificationContextService build() {
        if (CONTEXT_SERVICE == null) {
            CONTEXT_SERVICE = buildService();
        }
        return CONTEXT_SERVICE;
    }

    private CreateVerificationContextService buildService() {
        return CreateVerificationContextService.builder()
                .requestConverter(new VerificationContextRequestConverter())
                .identityService(identityServiceFactory.getIdentityService())
                .policiesService(new UkVerificationPoliciesService())
                .timeService(new DefaultTimeService())
                .eligibleMethodsService(new UkEligibleMethodsService())
                .expiryCalculator(new FixedExpiryCalculator())
                .idGenerator(new RandomUuidGenerator())
                .dao(dao)
                .build();
    }

    private static VerificationContextDao buildDao() {
        final ObjectMapper mapper = JsonApiVerificationContextObjectMapperSingleton.get();
        final VerificationContextDaoFactory factory = new DynamoVerificationContextDaoFactory(new Environment(), mapper);
        return factory.build();
    }

}
