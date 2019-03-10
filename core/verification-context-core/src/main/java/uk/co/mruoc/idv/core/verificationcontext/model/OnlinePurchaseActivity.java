package uk.co.mruoc.idv.core.verificationcontext.model;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OnlinePurchaseActivity extends DefaultActivity {

    public OnlinePurchaseActivity(final Instant timestamp, final String merchant, final String reference, final MonetaryAmount cost) {
        super(Types.ONLINE_PURCHASE, timestamp, toMap(merchant, reference, cost));
    }

    public String getMerchant() {
        return get("merchant", String.class);
    }

    public String getReference() {
        return get("reference", String.class);
    }

    public MonetaryAmount getCost() {
        return get("cost", MonetaryAmount.class);
    }

    private static Map<String, Object> toMap(final String merchant, final String reference, final MonetaryAmount cost) {
        final Map<String, Object> map = new HashMap<>();
        map.put("merchant", merchant);
        map.put("reference", reference);
        map.put("cost", cost);
        return Collections.unmodifiableMap(map);
    }

}
