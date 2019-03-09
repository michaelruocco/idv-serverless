package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.BukCustomerIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAlias;
import uk.co.mruoc.idv.core.identity.service.AliasLoader;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderService;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.IdvIdGenerator;
import uk.co.mruoc.idv.dao.identity.DynamoIdentityDao;
import uk.co.mruoc.idv.plugin.identity.aliasloader.as3.FakeAs3UkcCardholderIdAliasLoader;
import uk.co.mruoc.idv.plugin.identity.aliasloader.rsa.FakeRsaCreditCardNumberAliasLoader;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class IdentityServiceSingleton {

    private static IdentityService SERVICE;

    private IdentityServiceSingleton() {
        // utility class
    }

    public static IdentityService get() {
        final IdentityDao dao = buildIdentityDao();
        return get(dao);
    }

    private static IdentityService get(final IdentityDao dao) {
        if (SERVICE == null) {
            SERVICE = buildService(dao);
        }
        return SERVICE;
    }

    private static IdentityService buildService(final IdentityDao dao) {
        dao.save(Identity.withAliases(
                new IdvIdAlias(UUID.fromString("3713f6f6-8fa6-4686-bcbc-e348ee3b4b06")),
                new UkcCardholderIdAlias("12345678"),
                new BukCustomerIdAlias("1111111111"))
        );
        final Collection<AliasLoader> aliasLoaders = Arrays.asList(
                new FakeAs3UkcCardholderIdAliasLoader(),
                new FakeRsaCreditCardNumberAliasLoader()
        );
        final AliasLoaderService aliasLoaderService = new AliasLoaderService(aliasLoaders);
        final IdvIdGenerator idvIdGenerator = new IdvIdGenerator();
        return new IdentityService(dao, aliasLoaderService, idvIdGenerator);
    }

    private static IdentityDao buildIdentityDao() {
        final String region = System.getenv("REGION");
        final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(region)
                .build();
        final String stage = System.getenv("STAGE");
        final String tableName = String.format("%s-identity", stage);
        return new DynamoIdentityDao(new DynamoDB(client), tableName);
    }

}
