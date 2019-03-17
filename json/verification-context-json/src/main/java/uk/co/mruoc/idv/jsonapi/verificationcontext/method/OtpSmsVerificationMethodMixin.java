package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;

import java.util.Collection;

public abstract class OtpSmsVerificationMethodMixin {

    @JsonIgnore
    public abstract Passcode getPasscode();

    @JsonIgnore
    public abstract Collection<MobileNumber> getMobileNumbers();

}
