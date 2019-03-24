package uk.co.mruoc.idv.plugin.uk.identity.service;

import uk.co.mruoc.idv.core.identity.service.AliasLoader;
import uk.co.mruoc.idv.core.identity.service.DefaultAliasLoaderService;
import uk.co.mruoc.idv.plugin.uk.identity.service.as3.FakeAs3UkcCardholderIdAliasLoader;
import uk.co.mruoc.idv.plugin.uk.identity.service.rsa.FakeRsaCreditCardNumberAliasLoader;

import java.util.Arrays;
import java.util.Collection;

public class UkAliasLoaderService extends DefaultAliasLoaderService {

    private static final Collection<AliasLoader> LOADERS = Arrays.asList(
            new FakeAs3UkcCardholderIdAliasLoader(),
            new FakeRsaCreditCardNumberAliasLoader()
    );

    public UkAliasLoaderService() {
        super(LOADERS);
    }

}
