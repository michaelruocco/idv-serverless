package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.verificationcontext.LoadVerificationContextServiceFactory;
import uk.co.mruoc.idv.core.verificationcontext.service.LoadVerificationContextService;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDaoFactory;
import uk.co.mruoc.idv.dao.verificationcontext.DynamoVerificationContextDaoFactory;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextJsonConverterFactory;

public class UkGetVerificationContextServiceFactory implements LoadVerificationContextServiceFactory {

    private static LoadVerificationContextService CONTEXT_SERVICE;

    private final VerificationContextDao dao;

    public UkGetVerificationContextServiceFactory() {
        this(buildDao());
    }

    public UkGetVerificationContextServiceFactory(final VerificationContextDao dao) {
        this.dao = dao;
    }

    @Override
    public LoadVerificationContextService build() {
        if (CONTEXT_SERVICE == null) {
            CONTEXT_SERVICE = buildService();
        }
        return CONTEXT_SERVICE;
    }

    private LoadVerificationContextService buildService() {
        return LoadVerificationContextService.builder()
                .dao(dao)
                .build();
    }

    private static VerificationContextDao buildDao() {
        final JsonConverter converter = new VerificationContextJsonConverterFactory().build();
        final VerificationContextDaoFactory factory = new DynamoVerificationContextDaoFactory(new Environment(), converter);
        return factory.build();
    }

}
