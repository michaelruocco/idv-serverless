package uk.co.mruoc.idv.core.identity.service;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableCollection;

public class AliasLoaderService {

    private final Collection<AliasLoader> loaders;

    public AliasLoaderService(final Collection<AliasLoader> loaders) {
        this.loaders = unmodifiableCollection(loaders);
    }

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

    public static class AliasTypeNotSupportedException extends RuntimeException {

        private static final String MESSAGE_FORMAT = "alias type %s is not supported for channel %s";

        private final String aliasTypeName;
        private final String channelId;

        public AliasTypeNotSupportedException(final String aliasTypeName, final String channelId) {
            super(buildMessage(aliasTypeName, channelId));
            this.aliasTypeName = aliasTypeName;
            this.channelId = channelId;
        }

        private static String buildMessage(final String aliasTypeName, final String channelId) {
            return String.format(MESSAGE_FORMAT, aliasTypeName, channelId);
        }

        public String getAliasTypeName() {
            return aliasTypeName;
        }

        public String getChannelId() {
            return channelId;
        }

    }

}
