package uk.co.mruoc.idv.plugin.uk.awslambda.lockoutstate;

import uk.co.mruoc.idv.awslambda.lockoutdecision.PostVerificationAttemptHandler;
import uk.co.mruoc.idv.core.lockoutstate.UpdateLockoutStateService;

public class UkPostVerificationAttemptHandler extends PostVerificationAttemptHandler {

    public UkPostVerificationAttemptHandler() {
        this(new UkUpdateLockoutStateServiceFactory().build());
    }

    public UkPostVerificationAttemptHandler(final UpdateLockoutStateService service) {
        super(service);
    }

}
