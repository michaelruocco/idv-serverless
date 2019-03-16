package uk.co.mruoc.idv.jsonapi.verificationcontext.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.OnlinePurchaseActivity;

import javax.money.MonetaryAmount;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;

@Slf4j
public class OnlinePurchaseActivityDeserializer extends StdDeserializer<OnlinePurchaseActivity> {

    public OnlinePurchaseActivityDeserializer() {
        super(OnlinePurchaseActivity.class);
    }

    @Override
    public OnlinePurchaseActivity deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode activityNode = parser.readValueAsTree();
        return toOnlinePurchaseActivity(activityNode);
    }

    public static OnlinePurchaseActivity toOnlinePurchaseActivity(final JsonNode activityNode) {
        final Instant timestamp = extractTimestamp(activityNode);
        final JsonNode propertiesNode = extractPropertiesNode(activityNode);
        final String merchant = propertiesNode.get("merchant").asText();
        final String reference = propertiesNode.get("reference").asText();
        final MonetaryAmount cost = extractCost(propertiesNode);
        return new OnlinePurchaseActivity(timestamp, merchant, reference, cost);
    }

    private static Instant extractTimestamp(final JsonNode activityNode) {
        return Instant.parse(activityNode.get("timestamp").asText());
    }

    private static MonetaryAmount extractCost(final JsonNode propertiesNode) {
        final JsonNode costNode = propertiesNode.get("cost");
        final BigDecimal amount = new BigDecimal(costNode.get("amount").asText());
        final String currency = costNode.get("currency").asText();
        return Money.of(amount, currency);
    }

    private static JsonNode extractPropertiesNode(final JsonNode node) {
        return node.get("properties");
    }

}
