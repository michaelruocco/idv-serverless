package uk.co.mruoc.idv.dao.verificationattempts;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;
import uk.co.mruoc.idv.json.JsonConverter;

import java.util.Optional;
import java.util.UUID;

@Builder
@Slf4j
public class DynamoVerificationAttemptsDao implements VerificationAttemptsDao {

    private final JsonConverter converter;
    private final Table table;

    @Override
    public Optional<VerificationAttempts> loadByIdvId(final UUID idvId) {
        log.info("loading verification attempts by idv id {}", idvId);
        final Item item = table.getItem("idvId", idvId.toString());
        if (item == null) {
            log.debug("verification attempt not found returning empty optional");
            return Optional.empty();
        }
        log.info("loaded item {}", item);
        final VerificationAttempts attempts = toAttempts(item);
        log.info("returning attempts {}", attempts);
        return Optional.of(attempts);
    }

    @Override
    public void save(final VerificationAttempts attempts) {
        log.info("saving verification attempts {}", attempts);
        final Item item = toItem(attempts);
        log.info("putting item {}", item);
        table.putItem(item);
    }

    private VerificationAttempts toAttempts(final Item item) {
        final String json = item.getJSON("body");
        log.info("loaded json {}", json);
        return converter.toObject(json, VerificationAttempts.class);
    }

    private Item toItem(final VerificationAttempts attempts) {
        final String json = converter.toJson(attempts);
        log.info("writing item with body {}", json);
        return new Item()
                .withPrimaryKey("idvId", attempts.getIdvId().toString())
                .withJSON("body", json);
    }

}
