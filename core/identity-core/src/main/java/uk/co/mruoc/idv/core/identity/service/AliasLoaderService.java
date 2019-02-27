package uk.co.mruoc.idv.core.identity.service;

import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

import java.util.Collection;

import static java.util.Collections.unmodifiableCollection;

public class AliasLoaderService {

    private final Collection<AliasLoader> loaders;

    public AliasLoaderService(final Collection<AliasLoader> loaders) {
        this.loaders = unmodifiableCollection(loaders);
    }

    public Aliases loadAliases(final AliasLoaderRequest request) {
        Aliases aggregatedAliases = Aliases.with(request.getAliases());
        for (final AliasLoader loader : loaders) {
            aggregatedAliases = aggregatedAliases.add(loader.load(request));
        }
        return aggregatedAliases;
    }

}
