package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.verificationcontext.LoadVerificationContextServiceFactory;
import uk.co.mruoc.idv.core.verificationcontext.service.LoadVerificationContextService;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDaoFactory;
import uk.co.mruoc.idv.dao.verificationcontext.DynamoVerificationContextDaoFactory;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextObjectMapperSingleton;

public class UkLoadVerificationContextServiceFactory implements LoadVerificationContextServiceFactory {

    private static LoadVerificationContextService CONTEXT_SERVICE;

    private final VerificationContextDao dao;

    public UkLoadVerificationContextServiceFactory() {
        this(buildDao());
    }

    public UkLoadVerificationContextServiceFactory(final VerificationContextDao dao) {
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
        final ObjectMapper mapper = VerificationContextObjectMapperSingleton.get();
        final VerificationContextDaoFactory factory = new DynamoVerificationContextDaoFactory(new Environment(), mapper);
        return factory.build();
    }

}
