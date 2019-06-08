package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class IdvJsonApiLockoutDecisionModule extends SimpleModule {

    public IdvJsonApiLockoutDecisionModule() {
        setMixInAnnotation(LockoutStateResponse.class, LockoutStateResponseMixin.class);
    }

}
