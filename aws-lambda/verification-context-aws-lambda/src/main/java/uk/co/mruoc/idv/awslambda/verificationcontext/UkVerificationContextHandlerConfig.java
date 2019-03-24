package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.identity.GetIdentityHandlerConfig;
import uk.co.mruoc.idv.awslambda.identity.UkGetIdentityHandlerConfig;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.service.FixedExpiryCalculator;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDaoFactory;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextRequestConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextService;
import uk.co.mruoc.idv.dao.verificationcontext.DynamoVerificationContextDaoFactory;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonApiVerificationContextObjectMapperSingleton;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility.UkEligibleMethodsService;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.UkVerificationPoliciesService;

public class UkVerificationContextHandlerConfig implements PostVerificationContextHandlerConfig {

    private static VerificationContextService CONTEXT_SERVICE;

    private final GetIdentityHandlerConfig identityHandlerConfig;
    private final VerificationContextDaoFactory daoFactory;

    public UkVerificationContextHandlerConfig() {
        this(new UkGetIdentityHandlerConfig(), buildDaoFactory());
    }

    public UkVerificationContextHandlerConfig(final GetIdentityHandlerConfig identityHandlerConfig, final VerificationContextDaoFactory daoFactory) {
        this.identityHandlerConfig = identityHandlerConfig;
        this.daoFactory = daoFactory;
    }

    @Override
    public VerificationContextService getVerificationContextService() {
        if (CONTEXT_SERVICE == null) {
            CONTEXT_SERVICE = buildService();
        }
        return CONTEXT_SERVICE;
    }

    private VerificationContextService buildService() {
        return VerificationContextService.builder()
                .requestConverter(new VerificationContextRequestConverter())
                .identityService(identityHandlerConfig.getIdentityService())
                .policiesService(new UkVerificationPoliciesService())
                .timeService(new DefaultTimeService())
                .eligibleMethodsService(new UkEligibleMethodsService())
                .expiryCalculator(new FixedExpiryCalculator())
                .idGenerator(new RandomUuidGenerator())
                .dao(daoFactory.build())
                .build();
    }

    private static VerificationContextDaoFactory buildDaoFactory() {
        final ObjectMapper mapper = JsonApiVerificationContextObjectMapperSingleton.get();
        return new DynamoVerificationContextDaoFactory(new Environment(), mapper);
    }

}
