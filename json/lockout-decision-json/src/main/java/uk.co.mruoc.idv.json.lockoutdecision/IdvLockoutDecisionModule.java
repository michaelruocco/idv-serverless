package uk.co.mruoc.idv.json.lockoutdecision;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

public class IdvLockoutDecisionModule extends SimpleModule {

    public IdvLockoutDecisionModule() {
        setUpAttempts();
    }

    private void setUpAttempts() {
        addDeserializer(VerificationAttempts.class, new VerificationAttemptsDeserializer());

        setMixInAnnotation(VerificationAttempt.class, VerificationAttemptMixin.class);
        setMixInAnnotation(VerificationAttempts.class, VerificationAttemptsMixin.class);
        setMixInAnnotation(LockoutStateRequest.class, LockoutStateRequestMixin.class);
    }

}
