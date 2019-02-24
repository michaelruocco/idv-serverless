package uk.co.mruoc.idv.plugin.identity.aliasloader;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.BukCustomerIdAlias;
import uk.co.mruoc.idv.core.identity.service.DefaultAliasLoader;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderHandler;

import java.util.Collection;

import static java.util.Collections.singleton;

public class FakeAs3UkcCardholderIdAliasLoader extends DefaultAliasLoader {

    private static final Collection<String> SUPPORTED_CHANNEL_IDS = singleton("AS3");
    private static final Collection<AliasType> SUPPORTED_ALIAS_TYPES = singleton(AliasType.UKC_CARDHOLDER_ID);

    public FakeAs3UkcCardholderIdAliasLoader() {
        super(SUPPORTED_CHANNEL_IDS, SUPPORTED_ALIAS_TYPES, new Handler());
    }

    private static class Handler implements AliasLoaderHandler {

        @Override
        public Collection<Alias> loadAliases(Alias inputAlias) {
            return singleton(new BukCustomerIdAlias("1212121212"));
        }

    }

}
