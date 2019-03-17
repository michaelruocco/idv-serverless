package uk.co.mruoc.idv.jsonapi.verificationcontext.activity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javamoney.moneta.Money;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.DefaultActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.OnlinePurchaseActivity;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonLoader;
import uk.co.mruoc.idv.jsonapi.verificationcontext.ObjectMapperSingleton;

import javax.money.MonetaryAmount;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ActivityDeserializerTest {

    private static final String ONLINE_PURCHASE_ACTIVITY_PATH = "/activity/online-purchase-activity.json";
    private static final String LOGIN_ACTIVITY_PATH = "/activity/login-activity.json";
    private static final String DEFAULT_ACTIVITY_PATH = "/activity/default-activity.json";

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    public void shouldSerializeOnlinePurchaseActivity() throws JsonProcessingException  {
        final Activity activity = buildOnlinePurchaseActivity();

        final String json = MAPPER.writeValueAsString(activity);

        final String expectedJson = JsonLoader.loadJson(ONLINE_PURCHASE_ACTIVITY_PATH);
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializeOnlinePurchaseActivity() throws IOException {
        final String json = JsonLoader.loadJson(ONLINE_PURCHASE_ACTIVITY_PATH);

        final Activity activity = MAPPER.readValue(json, Activity.class);

        final Activity expectedActivity = buildOnlinePurchaseActivity();
        assertThat(activity).isEqualToComparingFieldByFieldRecursively(expectedActivity);
    }

    @Test
    public void shouldSerializeLoginActivity() throws JsonProcessingException  {
        final Activity activity = buildLoginActivity();

        final String json = MAPPER.writeValueAsString(activity);

        final String expectedJson = JsonLoader.loadJson(LOGIN_ACTIVITY_PATH);
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializeLoginActivity() throws IOException {
        final String json = JsonLoader.loadJson(LOGIN_ACTIVITY_PATH);

        final Activity activity = MAPPER.readValue(json, Activity.class);

        final Activity expectedActivity = buildLoginActivity();
        assertThat(activity).isEqualToComparingFieldByFieldRecursively(expectedActivity);
    }

    @Test
    public void shouldSerializeDefaultActivity() throws JsonProcessingException  {
        final Activity activity = buildDefaultActivity();

        final String json = MAPPER.writeValueAsString(activity);

        final String expectedJson = JsonLoader.loadJson(DEFAULT_ACTIVITY_PATH);
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializeDefaultActivity() throws IOException {
        final String json = JsonLoader.loadJson(DEFAULT_ACTIVITY_PATH);

        final Activity activity = MAPPER.readValue(json, Activity.class);

        final Activity expectedActivity = buildDefaultActivity();
        assertThat(activity).isEqualToComparingFieldByFieldRecursively(expectedActivity);
    }

    private static Activity buildOnlinePurchaseActivity() {
        final Instant timestamp = Instant.parse("2019-03-10T12:53:57.547Z");
        final String merchant = "Amazon";
        final String reference = "ABC123";
        final MonetaryAmount cost = Money.of(10.99, "GBP");
        return new OnlinePurchaseActivity(timestamp, merchant, reference, cost);
    }

    private static Activity buildLoginActivity() {
        final Instant timestamp = Instant.parse("2019-03-10T12:53:57.547Z");
        return new LoginActivity(timestamp);
    }

    private static Activity buildDefaultActivity() {
        final Map<String, Object> subProperty = new HashMap<>();
        subProperty.put("subProperty", "value");

        final Map<String, Object> properties = new HashMap<>();
        properties.put("property", 1);
        properties.put("anotherProperty", Collections.unmodifiableMap(subProperty));

        final Instant timestamp = Instant.parse("2019-03-10T12:53:57.547Z");
        return new DefaultActivity("DEFAULT_ACTIVITY", timestamp, Collections.unmodifiableMap(properties));
    }

}
