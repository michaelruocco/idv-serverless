package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;

public class IdvJsonApiVerificationContextModule extends SimpleModule {

    public IdvJsonApiVerificationContextModule() {
        setMixInAnnotation(VerificationContext.class, JsonApiVerificationContextMixin.class);
    }

}
