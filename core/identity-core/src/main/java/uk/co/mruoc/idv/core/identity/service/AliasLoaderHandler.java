package uk.co.mruoc.idv.core.identity.service;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import java.util.Collection;

public interface AliasLoaderHandler {

    Collection<Alias> loadAliases(final Alias inputAlias);

}
