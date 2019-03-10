package uk.co.mruoc.idv.core.identity.service;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

import java.util.Optional;

@Slf4j
public class IdentityService {

    private final IdentityDao dao;
    private final AliasLoaderService aliasLoaderService;
    private final IdvIdGenerator idvIdGenerator;

    public IdentityService(final IdentityDao dao, final AliasLoaderService aliasLoaderService, final IdvIdGenerator idvIdGenerator) {
        this.dao = dao;
        this.aliasLoaderService = aliasLoaderService;
        this.idvIdGenerator = idvIdGenerator;
    }

    public Identity upsert(final UpsertIdentityRequest request) {
        log.info("upserting identity with request {}", request);
        final Identity identity = loadOrCreateIdentity(request);
        log.debug("loaded identity {}", identity);
        final Aliases loadedAliases = loadAliases(request.getChannelId(), identity);
        log.debug("loaded aliases {}", loadedAliases);
        final Identity identityWithAliases = identity.addAliases(loadedAliases);
        log.info("saving identity {}", identity);
        dao.save(identityWithAliases);
        return identityWithAliases;
    }

    public Identity load(final Alias alias) {
        log.info("loading identity using alias {}", alias);
        return dao.load(alias).orElseThrow(() -> new IdentityNotFoundException(alias));
    }

    private Identity loadOrCreateIdentity(final UpsertIdentityRequest request) {
        final Optional<Identity> identity = dao.load(request.getAlias());
        return identity.orElseGet(() -> createNewIdentity(request.getAlias()));
    }

    private Identity createNewIdentity(final Alias requestAlias) {
        final Alias idvId = idvIdGenerator.generate();
        final Aliases aliases = Aliases.with(idvId, requestAlias);
        log.info("creating new identity aliases {}", aliases);
        return Identity.withAliases(aliases);
    }

    private Aliases loadAliases(final String channelId, final Identity identity) {
        final AliasLoaderRequest loaderRequest = AliasLoaderRequest.builder()
                .channelId(channelId)
                .aliases(identity.getAliases())
                .build();
        log.info("loading aliases with alias loader request {}", loaderRequest);
        return aliasLoaderService.loadAliases(loaderRequest);
    }

    public static class IdentityNotFoundException extends RuntimeException {

        private final Alias alias;

        public IdentityNotFoundException(final Alias alias) {
            super(alias.toString());
            this.alias = alias;
        }

        public Alias getAlias() {
            return alias;
        }

    }

}
