package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.mruoc.idv.core.model.CardNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;

import java.util.Collection;

public abstract class PhysicalPinsentryVerificationMethodMixin {

    @JsonIgnore
    public abstract PinsentryFunction getFunction();

    @JsonIgnore
    public abstract Collection<CardNumber> getCardNumbers();

}
