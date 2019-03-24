package uk.co.mruoc.idv.awslambda.identity;

import uk.co.mruoc.idv.awslambda.Environment;
import uk.co.mruoc.idv.core.identity.service.AliasLoader;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderService;
import uk.co.mruoc.idv.core.identity.service.IdentityDaoFactory;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.IdvIdGenerator;
import uk.co.mruoc.idv.dao.identity.DynamoIdentityDaoFactory;
import uk.co.mruoc.idv.plugin.uk.identity.service.as3.FakeAs3UkcCardholderIdAliasLoader;
import uk.co.mruoc.idv.plugin.uk.identity.service.rsa.FakeRsaCreditCardNumberAliasLoader;

import java.util.Arrays;
import java.util.Collection;

public class UkGetIdentityHandlerConfig implements GetIdentityHandlerConfig {

    private static IdentityService IDENTITY_SERVICE;

    private final IdentityDaoFactory daoFactory;

    public UkGetIdentityHandlerConfig() {
        this(new DynamoIdentityDaoFactory(new Environment()));
    }

    public UkGetIdentityHandlerConfig(final IdentityDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public IdentityService getIdentityService() {
        if (IDENTITY_SERVICE == null) {
            IDENTITY_SERVICE = buildService();
        }
        return IDENTITY_SERVICE;
    }

    private IdentityService buildService() {
        return IdentityService.builder()
                .dao(daoFactory.build())
                .aliasLoaderService(buildAliasLoaderService())
                .idvIdGenerator(new IdvIdGenerator())
                .build();
    }

    private static AliasLoaderService buildAliasLoaderService() {
        final Collection<AliasLoader> aliasLoaders = Arrays.asList(
                new FakeAs3UkcCardholderIdAliasLoader(),
                new FakeRsaCreditCardNumberAliasLoader()
        );
        return new AliasLoaderService(aliasLoaders);
    }

}
