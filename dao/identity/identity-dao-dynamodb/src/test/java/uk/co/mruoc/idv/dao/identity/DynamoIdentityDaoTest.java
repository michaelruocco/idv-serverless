package uk.co.mruoc.idv.dao.identity;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedDebitCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamoIdentityDaoTest {

    private static final String TABLE_NAME = "identity";

    @Rule
    public final LocalDynamoRule localDynamoRule = new LocalDynamoRule();

    private final IdentityTableFactory tableFactory = new IdentityTableFactory(TABLE_NAME);

    private IdentityDao dao;

    @Before
    public void setUp() throws InterruptedException {
        final AmazonDynamoDB client = localDynamoRule.getClient();
        tableFactory.createTable(client);

        dao = new DynamoIdentityDao(new DynamoDB(client), TABLE_NAME);
    }

    @Test
    public void shouldSaveIdentityAndLoadByAnyAlias() {
        final Identity identity = Identity.withAliases(
                new IdvIdAlias(),
                new TokenizedCreditCardNumberAlias("1234567890123456"),
                new TokenizedDebitCardNumberAlias("1111111111111111")
        );

        dao.save(identity);

        identity.getAliases().forEach(alias -> assertThat(dao.load(alias).get().getAliases()).containsExactlyInAnyOrderElementsOf(identity.getAliases()));
    }

    @Test
    public void identityShouldRemainSameIfSameIdentityIsSavedTwice() {
        final Identity identity = Identity.withAliases(
                new IdvIdAlias(),
                new TokenizedDebitCardNumberAlias("1234567890123456"),
                new TokenizedCreditCardNumberAlias("4929123456789012")
        );

        dao.save(identity);
        identity.getAliases().forEach(alias -> assertThat(dao.load(alias).get().getAliases()).containsExactlyInAnyOrderElementsOf(identity.getAliases()));

        dao.save(identity);
        identity.getAliases().forEach(alias -> assertThat(dao.load(alias).get().getAliases()).containsExactlyInAnyOrderElementsOf(identity.getAliases()));
    }

    @Test
    public void shouldReturnEmptyOptionalWhenIdentityNotFound() {
        Optional<Identity> result = dao.load(new IdvIdAlias());

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void shouldDeleteIdentity() {
        final Identity identity = Identity.withAliases(
                new IdvIdAlias(),
                new TokenizedCreditCardNumberAlias("1234567890123456"),
                new TokenizedDebitCardNumberAlias("4929123456789012")
        );

        dao.save(identity);
        identity.getAliases().forEach(alias -> assertThat(dao.load(alias).get().getAliases()).containsExactlyInAnyOrderElementsOf(identity.getAliases()));

        dao.delete(identity);
        identity.getAliases().forEach(alias -> assertThat(dao.load(alias)).isEmpty());
    }

}
