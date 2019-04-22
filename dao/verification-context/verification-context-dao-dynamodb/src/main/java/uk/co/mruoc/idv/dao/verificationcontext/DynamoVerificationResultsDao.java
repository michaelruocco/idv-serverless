package uk.co.mruoc.idv.dao.verificationcontext;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationResultsDao;
import uk.co.mruoc.idv.json.JsonConverter;

import java.util.Optional;
import java.util.UUID;

@Builder
@Slf4j
public class DynamoVerificationResultsDao implements VerificationResultsDao {

    private final JsonConverter converter;
    private final Table table;

    @Override
    public void save(final VerificationMethodResults results) {
        log.info("saving verification context {}", results);
        final Item item = toItem(results);
        log.info("putting item {}", item);
        table.putItem(item);
    }

    @Override
    public Optional<VerificationMethodResults> load(final UUID contextId) {
        log.info("loading verification context by id {}", contextId);
        final Item item = table.getItem("id", contextId.toString());
        if (item == null) {
            log.debug("verification results not found returning empty optional");
            return Optional.empty();
        }
        log.info("loaded item {}", item);
        final VerificationMethodResults results = toResults(item);
        log.info("returning results {}", results);
        return Optional.of(results);
    }

    private Item toItem(final VerificationMethodResults results) {
        final String json = converter.toJson(results);
        log.info("writing item with body {}", json);
        return new Item()
                .withPrimaryKey("id", results.getContextId().toString())
                .withJSON("body", json);
    }

    private VerificationMethodResults toResults(final Item item) {
        final String json = item.getJSON("body");
        log.info("loaded json {}", json);
        return converter.toObject(json, VerificationMethodResults.class);
    }

}
