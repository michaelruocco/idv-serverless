package uk.co.mruoc.idv.plugin.uk.awslambda.lockoutstate;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.lockoutdecision.GetLockoutStateHandler;
import uk.co.mruoc.idv.awslambda.lockoutdecision.LoadVerificationAttemptsServiceFactory;
import uk.co.mruoc.idv.core.lockoutstate.service.LockoutStateCalculationService;
import uk.co.mruoc.idv.core.verificationattempts.service.VerificationAttemptsService;
import uk.co.mruoc.idv.core.lockoutstate.service.LoadLockoutStateService;
import uk.co.mruoc.idv.core.lockoutstate.service.VerificationAttemptsConverter;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa.UkLockoutPoliciesService;

@Slf4j
public class UkGetLockoutStateHandler extends GetLockoutStateHandler {

    public UkGetLockoutStateHandler() {
        this(new UkLoadVerificationAttemptsServiceFactory());
    }

    public UkGetLockoutStateHandler(final LoadVerificationAttemptsServiceFactory factory) {
        this(factory.build());
    }

    public UkGetLockoutStateHandler(final VerificationAttemptsService loadAttemptsService) {
        super(LoadLockoutStateService.builder()
                .policiesService(new UkLockoutPoliciesService())
                .attemptsService(loadAttemptsService)
                .calculationService(new LockoutStateCalculationService(new VerificationAttemptsConverter(new DefaultTimeService())))
                .build());
    }

}
