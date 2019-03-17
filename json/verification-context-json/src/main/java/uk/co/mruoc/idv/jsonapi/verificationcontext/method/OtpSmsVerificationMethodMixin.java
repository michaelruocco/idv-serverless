package uk.co.mruoc.idv.jsonapi.verificationcontext.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;

import java.util.Collection;

public interface OtpSmsVerificationMethodMixin {

    @JsonIgnore
    Passcode getPasscode();

    @JsonIgnore
    Collection<MobileNumber> getMobileNumbers();

}
