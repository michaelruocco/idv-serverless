package uk.co.mruoc.idv.json.verificationcontext.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.DefaultActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.OnlinePurchaseActivity;

import javax.money.MonetaryAmount;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class ActivityDeserializer extends StdDeserializer<Activity> {

    private final ObjectMapper mapper;

    public ActivityDeserializer(final ObjectMapper mapper) {
        super(Activity.class);
        this.mapper = mapper;
    }

    @Override
    public Activity deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode activityNode = parser.readValueAsTree();
        return toActivity(activityNode);
    }

    public Activity toActivity(final JsonNode activityNode) {
        final String type = activityNode.get("type").asText();
        switch (type) {
            case Activity.Types.LOGIN:
                return toLoginActivity(activityNode);
            case Activity.Types.ONLINE_PURCHASE:
                return toOnlinePurchaseActivity(activityNode);
            default:
                return toDefaultActivity(activityNode);
        }
    }

    private static LoginActivity toLoginActivity(final JsonNode activityNode) {
        final Instant timestamp = extractTimestamp(activityNode);
        return new LoginActivity(timestamp);
    }

    private static OnlinePurchaseActivity toOnlinePurchaseActivity(final JsonNode activityNode) {
        final Instant timestamp = extractTimestamp(activityNode);
        final JsonNode propertiesNode = extractPropertiesNode(activityNode);
        final String merchant = propertiesNode.get("merchant").asText();
        final String reference = propertiesNode.get("reference").asText();
        final MonetaryAmount cost = extractCost(propertiesNode);
        return new OnlinePurchaseActivity(timestamp, merchant, reference, cost);
    }

    private static MonetaryAmount extractCost(final JsonNode propertiesNode) {
        final JsonNode costNode = propertiesNode.get("cost");
        final BigDecimal amount = new BigDecimal(costNode.get("amount").asText());
        final String currency = costNode.get("currency").asText();
        return Money.of(amount, currency);
    }

    private DefaultActivity toDefaultActivity(final JsonNode activityNode) {
        final String type = extractType(activityNode);
        final Instant timestamp = extractTimestamp(activityNode);
        final Map<String, Object> properties = extractProperties(activityNode);
        return new DefaultActivity(type, timestamp, properties);
    }

    private Map<String, Object> extractProperties(final JsonNode activityNode) {
        final JsonNode propertiesNode = extractPropertiesNode(activityNode);
        final Map<String, Object> properties = mapper.convertValue(propertiesNode, Map.class);
        return Collections.unmodifiableMap(properties);
    }

    private static Instant extractTimestamp(final JsonNode activityNode) {
        return Instant.parse(activityNode.get("timestamp").asText());
    }

    private static JsonNode extractPropertiesNode(final JsonNode node) {
        return node.get("properties");
    }

    private static String extractType(final JsonNode activityNode) {
        return activityNode.get("type").asText();
    }

}
