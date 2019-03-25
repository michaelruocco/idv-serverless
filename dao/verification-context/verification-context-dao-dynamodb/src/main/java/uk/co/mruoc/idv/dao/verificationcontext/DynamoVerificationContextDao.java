package uk.co.mruoc.idv.dao.verificationcontext;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.UUID;

@Builder
@Slf4j
public class DynamoVerificationContextDao implements VerificationContextDao {

    private final ObjectMapper mapper;
    private final Table table;

    @Override
    public void save(final VerificationContext context) {
        log.info("saving verification context {}", context);
        final Item item = toItem(context);
        log.info("putting item {}", item);
        table.putItem(item);
    }

    @Override
    public Optional<VerificationContext> load(final UUID id) {
        log.info("loading verification context by id {}", id);
        final Item item = table.getItem("id", id.toString());
        if (item == null) {
            log.debug("verifiction context not found returning empty optional");
            return Optional.empty();
        }
        log.info("loaded item {}", item);
        final VerificationContext context = toVerificationContext(item);
        log.info("returning context {}", context);
        return Optional.of(context);
    }

    private Item toItem(final VerificationContext context) {
        try {
            final String json = mapper.writeValueAsString(context);
            log.info("using mixin {}", mapper.findMixInClassFor(VerificationContext.class));
            log.info("writing item with body {}", json);
            return new Item()
                    .withPrimaryKey("id", context.getId().toString())
                    .withJSON("body", json);
        } catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    private VerificationContext toVerificationContext(final Item item) {
        try {
            final String json = item.getJSON("body");
            log.info("loaded json {}", json);
            return mapper.readValue(json, VerificationContext.class);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
