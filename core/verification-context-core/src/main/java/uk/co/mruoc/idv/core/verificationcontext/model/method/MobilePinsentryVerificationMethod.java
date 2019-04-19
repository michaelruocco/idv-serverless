package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.ToString;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ToString(callSuper = true)
public class MobilePinsentryVerificationMethod extends DefaultVerificationMethod {

    private static final String FUNCTION_PROPERTY_NAME = "function";

    public MobilePinsentryVerificationMethod(final int duration, final PinsentryFunction function) {
        this(duration, function, DEFAULT_STATUS, DEFAULT_MAX_ATTEMPTS);
    }

    public MobilePinsentryVerificationMethod(final int duration, final PinsentryFunction function, final VerificationStatus status, final int maxAttempts) {
        super(Names.MOBILE_PINSENTRY, duration, status, maxAttempts, toMap(function));
    }

    public PinsentryFunction getFunction() {
        return get(FUNCTION_PROPERTY_NAME, PinsentryFunction.class);
    }

    private static Map<String, Object> toMap(final PinsentryFunction function) {
        final Map<String, Object> map = new HashMap<>();
        map.put(FUNCTION_PROPERTY_NAME, function);
        return Collections.unmodifiableMap(map);
    }

}
