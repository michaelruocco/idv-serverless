package uk.co.mruoc.idv.json.verificationcontext.activity;

import org.javamoney.moneta.Money;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.DefaultActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.OnlinePurchaseActivity;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextJsonConverterFactory;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class ActivityDeserializerTest {

    private static final String ONLINE_PURCHASE_ACTIVITY_PATH = "/activity/online-purchase-activity.json";
    private static final String LOGIN_ACTIVITY_PATH = "/activity/login-activity.json";
    private static final String DEFAULT_ACTIVITY_PATH = "/activity/default-activity.json";

    private static final JsonConverter CONVERTER = new VerificationContextJsonConverterFactory().build();

    @Test
    public void shouldSerializeOnlinePurchaseActivity() {
        final Activity activity = buildOnlinePurchaseActivity();

        final String json = CONVERTER.toJson(activity);

        final String expectedJson = loadContentFromClasspath(ONLINE_PURCHASE_ACTIVITY_PATH);
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializeOnlinePurchaseActivity() {
        final String json = loadContentFromClasspath(ONLINE_PURCHASE_ACTIVITY_PATH);

        final Activity activity = CONVERTER.toObject(json, Activity.class);

        final Activity expectedActivity = buildOnlinePurchaseActivity();
        assertThat(activity).isEqualToComparingFieldByFieldRecursively(expectedActivity);
    }

    @Test
    public void shouldSerializeLoginActivity() {
        final Activity activity = buildLoginActivity();

        final String json = CONVERTER.toJson(activity);

        final String expectedJson = loadContentFromClasspath(LOGIN_ACTIVITY_PATH);
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializeLoginActivity() {
        final String json = loadContentFromClasspath(LOGIN_ACTIVITY_PATH);

        final Activity activity = CONVERTER.toObject(json, Activity.class);

        final Activity expectedActivity = buildLoginActivity();
        assertThat(activity).isEqualToComparingFieldByFieldRecursively(expectedActivity);
    }

    @Test
    public void shouldSerializeDefaultActivity() {
        final Activity activity = buildDefaultActivity();

        final String json = CONVERTER.toJson(activity);

        final String expectedJson = loadContentFromClasspath(DEFAULT_ACTIVITY_PATH);
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializeDefaultActivity() {
        final String json = loadContentFromClasspath(DEFAULT_ACTIVITY_PATH);

        final Activity activity = CONVERTER.toObject(json, Activity.class);

        final Activity expectedActivity = buildDefaultActivity();
        assertThat(activity).isEqualToComparingFieldByFieldRecursively(expectedActivity);
    }

    private static Activity buildOnlinePurchaseActivity() {
        final Instant timestamp = Instant.parse("2019-03-10T12:53:57.547Z");
        final String merchant = "Amazon";
        final String reference = "ABC123";
        final MonetaryAmount cost = Money.of(10.99, "GBP");
        return new OnlinePurchaseActivity(timestamp, merchant, reference, cost, buildAdditionalProperties());
    }

    private static Activity buildLoginActivity() {
        final Instant timestamp = Instant.parse("2019-03-10T12:53:57.547Z");
        return new LoginActivity(timestamp, buildAdditionalProperties());
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

    private static Map<String, Object> buildAdditionalProperties() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("additional", "property");
        return Collections.unmodifiableMap(properties);
    }

}
