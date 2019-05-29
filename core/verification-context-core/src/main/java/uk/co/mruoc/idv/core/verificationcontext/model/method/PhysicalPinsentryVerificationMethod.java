package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.ToString;
import uk.co.mruoc.idv.core.model.CardNumber;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ToString(callSuper = true)
public class PhysicalPinsentryVerificationMethod extends DefaultVerificationMethod {

    private static final String FUNCTION_PROPERTY_NAME = "function";
    private static final String CARD_NUMBERS_PROPERTY_NAME = "cardNumbers";

    public PhysicalPinsentryVerificationMethod(final int duration,
                                               final PinsentryFunction function,
                                               final Collection<CardNumber> cardNumbers) {
        this(duration, function, DEFAULT_STATUS, cardNumbers);
    }

    public PhysicalPinsentryVerificationMethod(final int duration,
                                               final PinsentryFunction function,
                                               final VerificationStatus status,
                                               final Collection<CardNumber> cardNumbers) {
        super(Names.PHYSICAL_PINSENTRY, duration, status, toMap(function, cardNumbers));
    }

    public PinsentryFunction getFunction() {
        return get(FUNCTION_PROPERTY_NAME, PinsentryFunction.class);
    }

    @SuppressWarnings("unchecked")
    public Collection<CardNumber> getCardNumbers() {
        return get(CARD_NUMBERS_PROPERTY_NAME, Collection.class);
    }

    private static Map<String, Object> toMap(final PinsentryFunction function, final Collection<CardNumber> cardNumbers) {
        final Map<String, Object> map = new HashMap<>();
        map.put(FUNCTION_PROPERTY_NAME, function);
        map.put(CARD_NUMBERS_PROPERTY_NAME, cardNumbers);
        return Collections.unmodifiableMap(map);
    }

}
