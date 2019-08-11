package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.ToString;
import uk.co.mruoc.idv.core.model.MobileNumber;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ToString(callSuper = true)
public class OtpSmsVerificationMethod extends DefaultVerificationMethod {

    private static final String PASSCODE_PROPERTY_NAME = "passcode";
    private static final String MOBILE_NUMBERS_PROPERTY_NAME = "mobileNumbers";

    public OtpSmsVerificationMethod(final int duration, final Passcode passcode, final Collection<MobileNumber> mobileNumbers) {
        this(duration, passcode, ELIGIBLE, mobileNumbers);
    }

    public OtpSmsVerificationMethod(final int duration, final Passcode passcode, final boolean eligible, final Collection<MobileNumber> mobileNumbers) {
        super(Names.ONE_TIME_PASSCODE_SMS, duration, eligible, toMap(passcode, mobileNumbers));
    }

    public Passcode getPasscode() {
        return get(PASSCODE_PROPERTY_NAME, Passcode.class);
    }

    public Collection<MobileNumber> getMobileNumbers() {
        return get(MOBILE_NUMBERS_PROPERTY_NAME, Collection.class);
    }

    private static Map<String, Object> toMap(final Passcode passcode, final Collection<MobileNumber> mobileNumbers) {
        final Map<String, Object> map = new HashMap<>();
        map.put(PASSCODE_PROPERTY_NAME, passcode);
        map.put(MOBILE_NUMBERS_PROPERTY_NAME, mobileNumbers);
        return Collections.unmodifiableMap(map);
    }

}
