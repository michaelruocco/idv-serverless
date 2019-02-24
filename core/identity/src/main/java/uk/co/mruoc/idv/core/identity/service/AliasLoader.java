package uk.co.mruoc.idv.core.identity.service;

import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

public interface AliasLoader {

    Aliases load(final AliasLoaderRequest request);

}
