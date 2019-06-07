package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateResponse;

public class IdvJsonApiLockoutDecisionModule extends SimpleModule {

    public IdvJsonApiLockoutDecisionModule() {
        setMixInAnnotation(LockoutStateRequest.class, LockoutStateRequestMixin.class);
        setMixInAnnotation(LockoutState.class, LockoutStateMixin.class);
    }

}
