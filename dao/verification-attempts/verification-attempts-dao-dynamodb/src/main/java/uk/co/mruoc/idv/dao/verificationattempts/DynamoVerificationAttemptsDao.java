package uk.co.mruoc.idv.dao.verificationattempts;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationattempts.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;
import uk.co.mruoc.idv.json.JsonConverter;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Builder
@Slf4j
public class DynamoVerificationAttemptsDao implements VerificationAttemptsDao {

    private final JsonConverter converter;
    private final Table idvIdAttemptsTable;
    private final Table contextIdAttemptsTable;

    @Override
    public void save(final VerificationAttempts attempts) {
        log.info("saving verification attempts {}", attempts);
        saveByIdvId(attempts);
        saveByContextId(attempts);
    }

    @Override
    public Optional<VerificationAttempts> loadByIdvId(final UUID idvId) {
        log.info("loading verification attempts by idv id {}", idvId);
        final Item item = idvIdAttemptsTable.getItem("idvId", idvId.toString());
        if (item == null) {
            log.debug("verification attempts not found returning empty optional");
            return Optional.empty();
        }
        log.info("loaded item {}", item);
        final VerificationAttempts attempts = toAttempts(item);
        log.info("returning attempts {}", attempts);
        return Optional.of(attempts);
    }

    @Override
    public Optional<VerificationAttempts> loadByContextId(final UUID contextId) {
        log.info("loading verification attempts by context id {}", contextId);
        final Item item = contextIdAttemptsTable.getItem("contextId", contextId.toString());
        if (item == null) {
            log.debug("verification attempts not found returning empty optional");
            return Optional.empty();
        }
        log.info("loaded item {}", item);
        final VerificationAttempts attempts = toAttempts(item);
        log.info("returning attempts {}", attempts);
        return Optional.of(attempts);
    }

    private void saveByIdvId(final VerificationAttempts attempts) {
        final Item item = toIdvIdAttemptsItem(attempts);
        log.info("putting idv id attempts item {}", item);
        idvIdAttemptsTable.putItem(item);
    }

    private Item toIdvIdAttemptsItem(final VerificationAttempts attempts) {
        final String json = converter.toJson(attempts);
        log.info("writing idv id attempts item with body {}", json);
        return new Item()
                .withPrimaryKey("idvId", attempts.getIdvId().toString())
                .withJSON("body", json);
    }

    private void saveByContextId(final VerificationAttempts attempts) {
        final Collection<UUID> contextIds = attempts.getContextIds();
        log.info("got context ids {} from attempts {}", contextIds, attempts);
        for (final UUID contextId : contextIds) {
            final VerificationAttempts updatedAttempts = loadUpdatedAttempts(contextId, attempts);
            log.info("got updated attempts {} for context id {}", updatedAttempts, contextId);
            final Item item = toContextIdAttemptsItem(contextId, updatedAttempts);
            log.info("putting context id {} attempts item {}", contextId, item);
            contextIdAttemptsTable.putItem(item);
        }
    }

    private VerificationAttempts loadUpdatedAttempts(final UUID contextId, final VerificationAttempts attempts) {
        final VerificationAttempts contextIdAttempts = attempts.getByContextId(contextId);
        final Optional<VerificationAttempts> existingAttempts = loadByContextId(contextId);
        if (existingAttempts.isPresent()) {
            return existingAttempts.get().addAll(contextIdAttempts);
        }
        return contextIdAttempts;
    }

    private Item toContextIdAttemptsItem(final UUID contextId, final VerificationAttempts attempts) {
        final String json = converter.toJson(attempts);
        log.info("writing context id attempts item with body {}", json);
        return new Item()
                .withPrimaryKey("contextId", contextId.toString())
                .withJSON("body", json);
    }

    private VerificationAttempts toAttempts(final Item item) {
        final String json = item.getJSON("body");
        log.info("loaded json {}", json);
        return converter.toObject(json, VerificationAttempts.class);
    }

}
