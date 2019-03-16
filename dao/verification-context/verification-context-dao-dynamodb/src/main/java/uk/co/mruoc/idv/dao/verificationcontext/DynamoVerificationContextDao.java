package uk.co.mruoc.idv.dao.verificationcontext;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextDao;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.UUID;

public class DynamoVerificationContextDao implements VerificationContextDao {

    private final ObjectMapper mapper;
    private final Table table;

    public DynamoVerificationContextDao(final DynamoDB client, final String tableName, final ObjectMapper mapper) {
        this.table = client.getTable(tableName);
        this.mapper = mapper;
    }

    @Override
    public void save(final VerificationContext context) {
        final Item item = toItem(context);
        table.putItem(item);
    }

    @Override
    public Optional<VerificationContext> load(final UUID id) {
        final Item item = table.getItem("id", id.toString());
        if (item == null) {
            return Optional.empty();
        }
        final VerificationContext context = toVerificationContext(item);
        return Optional.of(context);
    }

    private Item toItem(final VerificationContext context) {
        try {
            final String json = mapper.writeValueAsString(context);
            System.out.println(json);
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
            return mapper.readValue(json, VerificationContext.class);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
