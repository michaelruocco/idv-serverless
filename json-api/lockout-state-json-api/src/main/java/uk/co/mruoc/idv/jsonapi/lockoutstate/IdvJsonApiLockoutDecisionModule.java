package uk.co.mruoc.idv.jsonapi.lockoutstate;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.verificationattempts.model.RegisterAttemptRequest;
import uk.co.mruoc.idv.core.verificationattempts.model.RegisterAttemptsRequest;

public class IdvJsonApiLockoutDecisionModule extends SimpleModule {

    public IdvJsonApiLockoutDecisionModule() {
        setMixInAnnotation(LockoutStateRequest.class, LockoutStateRequestMixin.class);
        setMixInAnnotation(LockoutStateResponse.class, LockoutStateResponseMixin.class);

        setMixInAnnotation(RegisterAttemptsRequest.class, RegisterAttemptsRequestMixin.class);
        setMixInAnnotation(RegisterAttemptRequest.class, RegisterAttemptRequestMixin.class);
    }

}
