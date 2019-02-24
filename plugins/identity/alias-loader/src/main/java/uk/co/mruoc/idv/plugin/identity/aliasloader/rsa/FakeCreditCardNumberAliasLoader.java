package uk.co.mruoc.idv.plugin.identity.aliasloader.rsa;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.BukCustomerIdAlias;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderHandler;

import java.util.Collection;

import static java.util.Collections.singleton;

public class FakeCreditCardNumberAliasLoader implements AliasLoaderHandler {

    @Override
    public Collection<Alias> loadAliases(final Alias inputAlias) {
        return singleton(new BukCustomerIdAlias("3333333333"));
    }

}
