package uk.co.mruoc.idv.plugin.uk.awslambda.lockoutdecision;

import uk.co.mruoc.idv.awslambda.lockoutdecision.LoadVerificationAttemptsServiceFactory;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.service.LoadVerificationAttemptsService;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.plugin.uk.awslambda.identity.UkIdentityServiceFactory;

public class UkLoadVerificationAttemptsServiceFactory implements LoadVerificationAttemptsServiceFactory {

    private final VerificationAttemptsDao dao;

    public UkLoadVerificationAttemptsServiceFactory(final VerificationAttemptsDao dao) {
        this.dao = dao;
    }

    @Override
    public LoadVerificationAttemptsService build() {
        return LoadVerificationAttemptsService.builder()
                .dao(dao)
                .uuidGenerator(new RandomUuidGenerator())
                .identityService(new UkIdentityServiceFactory().build())
                .build();
    }

}
