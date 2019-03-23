package uk.co.mruoc.idv.awslambda.identity;

import uk.co.mruoc.idv.core.identity.service.AliasLoader;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderService;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.identity.service.IdvIdGenerator;
import uk.co.mruoc.idv.plugin.uk.identity.aliasloader.as3.FakeAs3UkcCardholderIdAliasLoader;
import uk.co.mruoc.idv.plugin.uk.identity.aliasloader.rsa.FakeRsaCreditCardNumberAliasLoader;

import java.util.Arrays;
import java.util.Collection;

public class IdentityServiceSingleton {

    private static IdentityService SERVICE;

    private IdentityServiceSingleton() {
        // utility class
    }

    public static IdentityService get(final IdentityDao dao) {
        if (SERVICE == null) {
            SERVICE = buildService(dao);
        }
        return SERVICE;
    }

    private static IdentityService buildService(final IdentityDao dao) {
        return IdentityService.builder()
                .dao(dao)
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
