package uk.co.mruoc.idv.plugin.identity.aliasloader.as3;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.BukCustomerIdAlias;
import uk.co.mruoc.idv.core.identity.service.AliasHandler;
import uk.co.mruoc.idv.core.identity.service.AliasLoadFailedException;

import java.util.Collection;

import static java.util.Collections.singleton;

public class FakeUkcCardholderIdAliasHandler implements AliasHandler {

    @Override
    public Collection<Alias> loadAliases(final Alias providedAlias) {
        if (providedAlias.getValue().endsWith("9")) {
            throw new AliasLoadFailedException(providedAlias);
        }
        return singleton(new BukCustomerIdAlias("1212121212"));
    }

}
