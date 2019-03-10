package uk.co.mruoc.idv.core.verificationcontext.model;

import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class OnlinePurchaseActivityTest {

    private static final Instant TIMESTAMP = Instant.now();
    private static final String MERCHANT = "merchant";
    private static final String REFERENCE = "reference";
    private static final MonetaryAmount COST = Money.of(10.99, "GBP");

    private final OnlinePurchaseActivity activity = new OnlinePurchaseActivity(TIMESTAMP, MERCHANT, REFERENCE, COST);

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

}
