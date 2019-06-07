package uk.co.mruoc.idv.plugin.uk.awslambda.lockoutdecision;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.awslambda.lockoutdecision.PutResetLockoutStateHandler;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDaoFactory;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutStateService;
import uk.co.mruoc.idv.core.lockoutdecision.service.VerificationAttemptsConverter;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.dao.lockoutdecision.DynamoVerificationAttemptsDaoFactory;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.lockoutdecision.LockoutDecisionJsonConverterFactory;
import uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa.UkLockoutPoliciesService;

@Slf4j
public class UkPutResetLockoutStateHandler extends PutResetLockoutStateHandler {

    public UkPutResetLockoutStateHandler() {
        super(LockoutStateService.builder()
                .policiesService(new UkLockoutPoliciesService())
                .loadAttemptsService(new UkLoadVerificationAttemptsServiceFactory(buildDao()).build())
                .converter(new VerificationAttemptsConverter(new DefaultTimeService()))
                .dao(buildDao())
                .build());
    }

    private static VerificationAttemptsDao buildDao() {
        final JsonConverter converter = new LockoutDecisionJsonConverterFactory().build();
        final VerificationAttemptsDaoFactory factory = new DynamoVerificationAttemptsDaoFactory(new Environment(), converter);
        return factory.build();
    }

}
