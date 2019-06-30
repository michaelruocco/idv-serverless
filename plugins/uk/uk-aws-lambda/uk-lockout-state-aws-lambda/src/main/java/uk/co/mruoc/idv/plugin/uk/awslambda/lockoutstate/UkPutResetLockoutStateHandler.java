package uk.co.mruoc.idv.plugin.uk.awslambda.lockoutstate;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.awslambda.lockoutdecision.LoadVerificationAttemptsServiceFactory;
import uk.co.mruoc.idv.awslambda.lockoutdecision.PutResetLockoutStateHandler;
import uk.co.mruoc.idv.core.lockoutstate.UpdateLockoutStateService;
import uk.co.mruoc.idv.core.lockoutstate.service.LockoutStateCalculationService;
import uk.co.mruoc.idv.core.verificationattempts.service.VerificationAttemptsService;
import uk.co.mruoc.idv.core.lockoutstate.service.VerificationAttemptsConverter;
import uk.co.mruoc.idv.core.service.DefaultTimeService;
import uk.co.mruoc.idv.plugin.uk.lockoutdecision.policy.rsa.UkLockoutPoliciesService;

@Slf4j
public class UkPutResetLockoutStateHandler extends PutResetLockoutStateHandler {

    public UkPutResetLockoutStateHandler() {
        this(new UkLoadVerificationAttemptsServiceFactory());
    }

    public UkPutResetLockoutStateHandler(final LoadVerificationAttemptsServiceFactory factory) {
        this(factory.build());
    }

    public UkPutResetLockoutStateHandler(final VerificationAttemptsService loadAttemptsService) {
        super(UpdateLockoutStateService.builder()
                .policiesService(new UkLockoutPoliciesService())
                .attemptsService(loadAttemptsService)
                .calculationService(new LockoutStateCalculationService(new VerificationAttemptsConverter(new DefaultTimeService())))
                .build());
    }

}
