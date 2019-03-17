package uk.co.mruoc.idv.jsonapi.verificationcontext.activity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javamoney.moneta.Money;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.OnlinePurchaseActivity;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonLoader;
import uk.co.mruoc.idv.jsonapi.verificationcontext.ObjectMapperSingleton;

import javax.money.MonetaryAmount;
import java.io.IOException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class OnlinePurchaseActivityDeserializerTest {

    private static final String JSON = JsonLoader.loadJson("/activity/online-purchase-activity.json");
    private static final Activity ACTIVITY = buildActivity();
    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    public void shouldSerializeActivity() throws JsonProcessingException  {
        final String json = MAPPER.writeValueAsString(ACTIVITY);

        assertThat(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeActivity() throws IOException {
        final Activity activity = MAPPER.readValue(JSON, OnlinePurchaseActivity.class);

        assertThat(activity).isEqualToComparingFieldByFieldRecursively(ACTIVITY);
    }

    private static Activity buildActivity() {
        final Instant timestamp = Instant.parse("2019-03-10T12:53:57.547Z");
        final String merchant = "Amazon";
        final String reference = "ABC123";
        final MonetaryAmount cost = Money.of(10.99, "GBP");
        return new OnlinePurchaseActivity(timestamp, merchant, reference, cost);
    }

}
