package uk.co.mruoc.idv.core.identity.service;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

import java.util.ArrayList;
import java.util.Collection;

public class DefaultAliasLoader implements AliasLoader {

    private final Collection<String> supportedChannelIds;
    private final Collection<AliasType> supportedAliasTypes;
    private final AliasLoaderHandler aliasLoaderHandler;

    public DefaultAliasLoader(final Collection<String> supportedChannelIds,
                              final Collection<AliasType> supportedAliasTypes,
                              final AliasLoaderHandler aliasLoaderHandler) {
        this.supportedChannelIds = supportedChannelIds;
        this.supportedAliasTypes = supportedAliasTypes;
        this.aliasLoaderHandler = aliasLoaderHandler;
    }

    @Override
    public Aliases load(final AliasLoaderRequest request) {
        if (!isChannelSupported(request.getChannelId())) {
            return Aliases.empty();
        }
        return loadAliases(request.getAliases());
    }

    private boolean isChannelSupported(final String channelId) {
        return supportedChannelIds.contains(channelId);
    }

    private Aliases loadAliases(final Aliases inputAliases) {
        final Collection<Alias> loadedAliases = new ArrayList<>();
        final Collection<Alias> supportedAliases = inputAliases.getByTypes(supportedAliasTypes);
        for (final Alias supportedAlias : supportedAliases) {
            loadedAliases.addAll(loadAliases(supportedAlias));
        }
        return Aliases.with(loadedAliases);
    }

    private Collection<Alias> loadAliases(final Alias inputAlias) {
        return aliasLoaderHandler.loadAliases(inputAlias);
    }

}
