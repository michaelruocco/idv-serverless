package uk.co.mruoc.idv.core.verificationcontext.model.method;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MobilePinsentryVerificationMethod extends DefaultVerificationMethod {

    private static final String FUNCTION_PROPERTY_NAME = "function";

    public MobilePinsentryVerificationMethod(final int duration, final PinsentryFunction function) {
        super(Names.MOBILE_PINSENTRY, duration, toMap(function));
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
