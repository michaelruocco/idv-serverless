package uk.co.mruoc.idv.json.verificationcontext.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.mruoc.idv.core.model.CardNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;

import java.util.Collection;

public interface PhysicalPinsentryVerificationMethodMixin {

    @JsonIgnore
    PinsentryFunction getFunction();

    @JsonIgnore
    Collection<CardNumber> getCardNumbers();

}
