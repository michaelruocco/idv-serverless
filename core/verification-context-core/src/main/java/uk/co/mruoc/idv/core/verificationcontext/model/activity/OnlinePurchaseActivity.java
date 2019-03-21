package uk.co.mruoc.idv.core.verificationcontext.model.activity;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OnlinePurchaseActivity extends DefaultActivity {

    private static final String MERCHANT_PROPERTY_NAME = "merchant";
    private static final String REFERENCE_PROPERTY_NAME = "reference";
    private static final String COST_PROPERTY_NAME = "cost";

    public OnlinePurchaseActivity(final Instant timestamp, final String merchant, final String reference, final MonetaryAmount cost, final Map<String, Object> properties) {
        super(Types.ONLINE_PURCHASE, timestamp, toMap(merchant, reference, cost, properties));
    }

    public OnlinePurchaseActivity(final Instant timestamp, final String merchant, final String reference, final MonetaryAmount cost) {
        super(Types.ONLINE_PURCHASE, timestamp, toMap(merchant, reference, cost));
    }

    public String getMerchant() {
        return get(MERCHANT_PROPERTY_NAME, String.class);
    }

    public String getReference() {
        return get(REFERENCE_PROPERTY_NAME, String.class);
    }

    public MonetaryAmount getCost() {
        return get(COST_PROPERTY_NAME, MonetaryAmount.class);
    }

    private static Map<String, Object> toMap(final String merchant, final String reference, final MonetaryAmount cost, final Map<String, Object> properties) {
        return appendSpecificProperties(merchant, reference, cost, properties);
    }

    private static Map<String, Object> toMap(final String merchant, final String reference, final MonetaryAmount cost) {
        return appendSpecificProperties(merchant, reference, cost, new HashMap<>());
    }

    private static Map<String, Object> appendSpecificProperties(final String merchant, final String reference, final MonetaryAmount cost, final Map<String, Object> properties) {
        final Map<String, Object> mergedProperties = new HashMap<>(properties);
        mergedProperties.put(MERCHANT_PROPERTY_NAME, merchant);
        mergedProperties.put(REFERENCE_PROPERTY_NAME, reference);
        mergedProperties.put(COST_PROPERTY_NAME, cost);
        return Collections.unmodifiableMap(mergedProperties);
    }

}
