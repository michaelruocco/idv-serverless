package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.verificationcontext.result.VerificationResultsServiceFactory;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDaoFactory;
import uk.co.mruoc.idv.core.lockoutdecision.service.LoadVerificationAttemptsService;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutStateService;
import uk.co.mruoc.idv.core.lockoutdecision.service.VerificationAttemptsConverter;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationMethodResultConverter;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultService;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultsDao;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultsDaoFactory;
import uk.co.mruoc.idv.dao.lockoutdecision.DynamoVerificationAttemptsDaoFactory;
import uk.co.mruoc.idv.dao.verificationcontext.DynamoVerificationResultsDaoFactory;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextJsonConverterFactory;
import uk.co.mruoc.idv.plugin.uk.awslambda.identity.UkIdentityServiceFactory;
import uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa.UkLockoutPoliciesService;

@Slf4j
public class UkPostVerificationResultServiceFactory implements VerificationResultsServiceFactory {

    private static final JsonConverter JSON_CONVERTER = new VerificationContextJsonConverterFactory().build();
    private static final Environment ENVIRONMENT = new Environment();

    private final GetVerificationContextService getContextService;
    private final IdentityService identityService;
    private final VerificationResultsDao resultsDao;
    private final VerificationAttemptsDao attemptsDao;

    public UkPostVerificationResultServiceFactory() {
        this(new UkGetVerificationContextServiceFactory().build(),
                new UkIdentityServiceFactory().build(),
                buildResultsDao(),
                buildAttemptsDao());
    }

    public UkPostVerificationResultServiceFactory(final GetVerificationContextService getContextService,
                                                  final IdentityService identityService,
                                                  final VerificationResultsDao resultsDao,
                                                  final VerificationAttemptsDao attemptsDao) {
        this.getContextService = getContextService;
        this.identityService = identityService;
        this.resultsDao = resultsDao;
        this.attemptsDao = attemptsDao;
    }

    @Override
    public VerificationResultService build() {
        return buildService();
    }

    private VerificationResultService buildService() {
        return VerificationResultService.builder()
                .getContextService(getContextService)
                .uuidGenerator(new RandomUuidGenerator())
                .dao(resultsDao)
                .converter(new VerificationMethodResultConverter())
                .lockoutStateService(buildLockoutStateService())
                .build();
    }

    private LockoutStateService buildLockoutStateService() {
        return LockoutStateService.builder()
                .converter(new VerificationAttemptsConverter(new DefaultTimeService()))
                .policiesService(new UkLockoutPoliciesService())
                .loadAttemptsService(buildLoadAttemptsService())
                .dao(attemptsDao)
                .build();
    }

    private LoadVerificationAttemptsService buildLoadAttemptsService() {
        return LoadVerificationAttemptsService.builder()
                .uuidGenerator(new RandomUuidGenerator())
                .identityService(identityService)
                .dao(attemptsDao)
                .build();
    }

    private static VerificationResultsDao buildResultsDao() {
        final VerificationResultsDaoFactory factory = new DynamoVerificationResultsDaoFactory(ENVIRONMENT, JSON_CONVERTER);
        return factory.build();
    }

    private static VerificationAttemptsDao buildAttemptsDao() {
        final VerificationAttemptsDaoFactory factory = new DynamoVerificationAttemptsDaoFactory(ENVIRONMENT, JSON_CONVERTER);
        return factory.build();
    }

}
