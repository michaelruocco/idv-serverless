package uk.co.mruoc.idv.core.identity.service;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableCollection;

public class DefaultAliasLoaderService implements AliasLoaderService {

    private final Collection<AliasLoader> loaders;

    public DefaultAliasLoaderService(final Collection<AliasLoader> loaders) {
        this.loaders = unmodifiableCollection(loaders);
    }

    @Override
    public Aliases loadAliases(final AliasLoaderRequest request) {
        final Collection<AliasLoader> supportedLoaders = findSupportedLoaders(request);
        if (supportedLoaders.isEmpty()) {
            final Alias providedAlias = request.getProvidedAlias();
            throw new AliasTypeNotSupportedException(providedAlias.getTypeName(), request.getChannelId());
        }
        return loadAliases(request, supportedLoaders);
    }

    private Collection<AliasLoader> findSupportedLoaders(final AliasLoaderRequest request) {
        return loaders.stream().filter(loader -> loader.supports(request)).collect(Collectors.toList());
    }

    private static Aliases loadAliases(final AliasLoaderRequest request, final Collection<AliasLoader> supportedLoaders) {
        Aliases aggregatedAliases = Aliases.with(request.getProvidedAlias());
        for (final AliasLoader loader : supportedLoaders) {
            aggregatedAliases = aggregatedAliases.add(loader.load(request));
        }
        return aggregatedAliases;
    }

}
