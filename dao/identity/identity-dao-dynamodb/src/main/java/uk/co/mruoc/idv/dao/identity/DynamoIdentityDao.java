package uk.co.mruoc.idv.dao.identity;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class DynamoIdentityDao implements IdentityDao {

    private final DynamoDB client;
    private final String tableName;
    private final Table table;

    public DynamoIdentityDao(final DynamoDB client, final String tableName) {
        this.client = client;
        this.tableName = tableName;
        this.table = client.getTable(tableName);
    }

    @Override
    public void save(final Identity identity) {
        log.info("saving identity {}", identity);
        final Collection<Item> itemsToPut = new ArrayList<>();
        final Set<String> aliasStrings = AliasConverter.toStrings(identity.getAliases());
        for (final Alias alias : identity.getAliases()) {
            final String aliasString = AliasConverter.toString(alias);
            itemsToPut.add(new Item()
                    .withPrimaryKey("alias", aliasString)
                    .withStringSet("aliases", aliasStrings));
        }
        final TableWriteItems items = new TableWriteItems(tableName).withItemsToPut(itemsToPut);
        log.info("performing batch write item with itemsToPut {}", itemsToPut);
        client.batchWriteItem(items);
    }

    @Override
    public Optional<Identity> load(final Alias alias) {
        log.info("loading identity using alias {}", alias);
        final String aliasString = AliasConverter.toString(alias);
        final Item item = table.getItem("alias", aliasString);
        if (item == null) {
            log.info("identity not found using alias {}", alias);
            return Optional.empty();
        }
        final Identity identity = toIdentity(item);
        log.info("loaded identity {} using alias {}", identity, alias);
        return Optional.of(identity);
    }

    @Override
    public void delete(final Identity identity) {
        log.info("deleting identity {}", identity);
        final Collection<PrimaryKey> keysToDelete = new ArrayList<>();
        for (final Alias alias : identity.getAliases()) {
            final String aliasString = AliasConverter.toString(alias);
            keysToDelete.add(new PrimaryKey("alias", aliasString));
        }
        final PrimaryKey[] keysToDeleteArray = keysToDelete.toArray(new PrimaryKey[0]);
        final TableWriteItems items = new TableWriteItems(tableName).withPrimaryKeysToDelete(keysToDeleteArray);
        log.info("performing batch write item with keysToDelete {}", keysToDelete);
        client.batchWriteItem(items);
    }

    private static Identity toIdentity(final Item item) {
        Collection<Alias> aliases = AliasConverter.toAliases(item.getList("aliases"));
        return Identity.withAliases(aliases);
    }

}
