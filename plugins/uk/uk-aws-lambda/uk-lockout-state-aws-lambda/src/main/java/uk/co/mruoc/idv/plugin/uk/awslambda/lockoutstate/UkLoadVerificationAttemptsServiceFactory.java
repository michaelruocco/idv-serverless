package uk.co.mruoc.idv.plugin.uk.awslambda.lockoutstate;

import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.lockoutdecision.LoadVerificationAttemptsServiceFactory;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDaoFactory;
import uk.co.mruoc.idv.core.verificationattempts.service.VerificationAttemptsService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.dao.verificationattempts.DynamoVerificationAttemptsDaoFactory;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.verificationattempts.VerificationAttemptsJsonConverterFactory;
import uk.co.mruoc.idv.plugin.uk.awslambda.identity.UkIdentityServiceFactory;

public class UkLoadVerificationAttemptsServiceFactory implements LoadVerificationAttemptsServiceFactory {

    private static final JsonConverter JSON_CONVERTER = new VerificationAttemptsJsonConverterFactory().build();
    private static final Environment ENVIRONMENT = new Environment();

    private final IdentityService identityService;
    private final VerificationAttemptsDao dao;

    public UkLoadVerificationAttemptsServiceFactory() {
        this(new UkIdentityServiceFactory().build(), buildAttemptsDao());
    }

    public UkLoadVerificationAttemptsServiceFactory(final IdentityService identityService, final VerificationAttemptsDao dao) {
        this.identityService = identityService;
        this.dao = dao;
    }

    @Override
    public VerificationAttemptsService build() {
        return VerificationAttemptsService.builder()
                .dao(dao)
                .uuidGenerator(new RandomUuidGenerator())
                .identityService(identityService)
                .build();
    }

    @Override
    public VerificationAttemptsDao getDao() {
        return dao;
    }

    private static VerificationAttemptsDao buildAttemptsDao() {
        final VerificationAttemptsDaoFactory factory = new DynamoVerificationAttemptsDaoFactory(ENVIRONMENT, JSON_CONVERTER);
        return factory.build();
    }

}
