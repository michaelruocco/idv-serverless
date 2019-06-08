package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;

public class IdvJsonApiLockoutDecisionModule extends SimpleModule {

    public IdvJsonApiLockoutDecisionModule() {
        setMixInAnnotation(LockoutStateResponse.class, LockoutStateResponseMixin.class);
    }

}
