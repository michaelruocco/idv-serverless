package uk.co.mruoc.idv.core.verificationcontext.model.activity;

import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class OnlinePurchaseActivityTest {

    private static final String PROPERTY_NAME_1 = "property1";
    private static final String PROPERTY_NAME_2 = "property2";

    private static final String VALUE1 = "value1";
    private static final String VALUE2 = "value2";

    private static final Instant TIMESTAMP = Instant.now();
    private static final String MERCHANT = "merchant";
    private static final String REFERENCE = "reference";
    private static final MonetaryAmount COST = Money.of(10.99, "GBP");

    private final OnlinePurchaseActivity activity = new OnlinePurchaseActivity(TIMESTAMP, MERCHANT, REFERENCE, COST, buildProperties());

    @Test
    public void shouldReturnType() {
        final String type = activity.getType();

        assertThat(type).isEqualTo(Activity.Types.ONLINE_PURCHASE);
    }

    @Test
    public void shouldReturnTimestamp() {
        final Instant timestamp = activity.getTimestamp();

        assertThat(timestamp).isEqualTo(TIMESTAMP);
    }

    @Test
    public void shouldReturnMerchant() {
        final String merchant = activity.getMerchant();

        assertThat(merchant).isEqualTo(MERCHANT);
    }

    @Test
    public void shouldReturnReference() {
        final String reference = activity.getReference();

        assertThat(reference).isEqualTo(REFERENCE);
    }

    @Test
    public void shouldReturnCost() {
        final MonetaryAmount cost = activity.getCost();

        assertThat(cost).isEqualTo(COST);
    }

    @Test
    public void shouldReturnPropertyValues() {
        final String value1 = activity.get(PROPERTY_NAME_1, String.class);
        final String value2 = activity.get(PROPERTY_NAME_2, String.class);

        assertThat(value1).isEqualTo(VALUE1);
        assertThat(value2).isEqualTo(VALUE2);
    }

    @Test
    public void shouldReturnOnlySpecificPropertiesIfNoPropertiesProvided() {
        final Activity activity = new OnlinePurchaseActivity(TIMESTAMP, MERCHANT, REFERENCE, COST);

        assertThat(activity.getProperties()).hasSize(3);
        assertThat(activity.getProperties()).containsEntry("merchant", MERCHANT);
        assertThat(activity.getProperties()).containsEntry("reference", REFERENCE);
        assertThat(activity.getProperties()).containsEntry("cost", COST);
    }

    private Map<String, Object> buildProperties() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put(PROPERTY_NAME_1, VALUE1);
        properties.put(PROPERTY_NAME_2, VALUE2);
        return Collections.unmodifiableMap(properties);
    }

}
