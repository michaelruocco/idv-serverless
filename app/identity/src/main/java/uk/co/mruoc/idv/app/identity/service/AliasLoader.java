package uk.co.mruoc.idv.app.identity.service;

import uk.co.mruoc.idv.app.identity.model.alias.Aliases;

public interface AliasLoader {

    Aliases load(final AliasLoaderRequest request);

}
