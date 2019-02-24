package uk.co.mruoc.idv.plugin.identity.aliasloader.as3;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.BukCustomerIdAlias;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderHandler;

import java.util.Collection;

import static java.util.Collections.singleton;

public class FakeUkcCardholderIdAliasLoader implements AliasLoaderHandler {

    @Override
    public Collection<Alias> loadAliases(Alias inputAlias) {
        return singleton(new BukCustomerIdAlias("1212121212"));
    }

}
