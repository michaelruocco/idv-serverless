package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;

public interface MobilePinsentryVerificationMethodMixin {

    @JsonIgnore
    PinsentryFunction getFunction();

}
