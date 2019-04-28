package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.verificationcontext.result.VerificationResultsServiceFactory;
import uk.co.mruoc.idv.core.service.RandomUuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultService;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultsDao;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultsDaoFactory;
import uk.co.mruoc.idv.dao.verificationcontext.DynamoVerificationResultsDaoFactory;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextJsonConverterFactory;

@Slf4j
public class UkPostVerificationResultServiceFactory implements VerificationResultsServiceFactory {

    private static final JsonConverter JSON_CONVERTER = new VerificationContextJsonConverterFactory().build();
    private static final Environment ENVIRONMENT = new Environment();

    private final GetVerificationContextService getContextService;
    private final VerificationResultsDao dao;

    public UkPostVerificationResultServiceFactory() {
        this(new UkGetVerificationContextServiceFactory().build(), buildDao());
    }

    public UkPostVerificationResultServiceFactory(final GetVerificationContextService getContextService,
                                                  final VerificationResultsDao dao) {
        this.getContextService = getContextService;
        this.dao = dao;
    }

    @Override
    public VerificationResultService build() {
        return buildService(dao);
    }

    private VerificationResultService buildService(final VerificationResultsDao dao) {
        return VerificationResultService.builder()
                .getContextService(getContextService)
                .uuidGenerator(new RandomUuidGenerator())
                .dao(dao)
                .build();
    }

    private static VerificationResultsDao buildDao() {
        final VerificationResultsDaoFactory factory = new DynamoVerificationResultsDaoFactory(ENVIRONMENT, JSON_CONVERTER);
        return factory.build();
    }

}
