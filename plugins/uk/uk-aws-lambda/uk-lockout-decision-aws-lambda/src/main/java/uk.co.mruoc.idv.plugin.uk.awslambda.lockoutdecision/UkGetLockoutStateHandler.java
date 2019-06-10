package uk.co.mruoc.idv.plugin.uk.awslambda.lockoutdecision;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.lockoutdecision.GetLockoutStateHandler;
import uk.co.mruoc.idv.awslambda.lockoutdecision.LoadVerificationAttemptsServiceFactory;
import uk.co.mruoc.idv.core.lockoutdecision.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.lockoutdecision.service.LoadVerificationAttemptsService;
import uk.co.mruoc.idv.core.lockoutdecision.service.LockoutStateService;
import uk.co.mruoc.idv.core.lockoutdecision.service.VerificationAttemptsConverter;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa.UkLockoutPoliciesService;

@Slf4j
public class UkGetLockoutStateHandler extends GetLockoutStateHandler {

    public UkGetLockoutStateHandler() {
        this(new UkLoadVerificationAttemptsServiceFactory());
    }

    public UkGetLockoutStateHandler(final LoadVerificationAttemptsServiceFactory factory) {
        this(factory.build(), factory.getDao());
    }

    public UkGetLockoutStateHandler(final LoadVerificationAttemptsService loadAttemptsService, final VerificationAttemptsDao dao) {
        super(LockoutStateService.builder()
                .policiesService(new UkLockoutPoliciesService())
                .loadAttemptsService(loadAttemptsService)
                .converter(new VerificationAttemptsConverter(new DefaultTimeService()))
                .dao(dao)
                .build());
    }

}
