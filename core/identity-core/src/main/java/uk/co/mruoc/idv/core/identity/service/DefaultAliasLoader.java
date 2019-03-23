package uk.co.mruoc.idv.core.identity.service;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

import java.util.Collection;

public class DefaultAliasLoader implements AliasLoader {

    private final Collection<String> supportedChannelIds;
    private final Collection<String> supportedAliasTypeNames;
    private final AliasHandler aliasHandler;

    public DefaultAliasLoader(final Collection<String> supportedChannelIds,
                              final Collection<String> supportedAliasTypeNames,
                              final AliasHandler aliasHandler) {
        this.supportedChannelIds = supportedChannelIds;
        this.supportedAliasTypeNames = supportedAliasTypeNames;
        this.aliasHandler = aliasHandler;
    }

    @Override
    public Aliases load(final AliasLoaderRequest request) {
        if (supports(request)) {
            return loadAliases(request.getProvidedAlias());
        }
        return Aliases.empty();
    }

    @Override
    public boolean supports(AliasLoaderRequest request) {
        return isChannelSupported(request.getChannelId()) &&
                isAliasSupported(request.getProvidedAlias());
    }

    private boolean isChannelSupported(final String channelId) {
        return supportedChannelIds.contains(channelId);
    }

    private boolean isAliasSupported(final Alias alias) {
        return supportedAliasTypeNames.contains(alias.getTypeName());
    }

    private Aliases loadAliases(final Alias providedAlias) {
        return Aliases.with(aliasHandler.loadAliases(providedAlias));
    }

}
