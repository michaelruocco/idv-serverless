package uk.co.mruoc.idv.core.identity.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

import java.util.Optional;

@Builder
@Slf4j
public class IdentityService {

    private final IdentityDao dao;
    private final AliasLoaderService aliasLoaderService;
    private final IdvIdGenerator idvIdGenerator;

    public Identity upsert(final UpsertIdentityRequest request) {
        log.info("upserting identity with request {}", request);
        return loadIdentity(request).orElseGet(() -> createNewIdentity(request));
    }

    public Identity load(final Alias alias) {
        log.info("loading identity using alias {}", alias);
        return dao.load(alias).orElseThrow(() -> new IdentityNotFoundException(alias));
    }

    private Optional<Identity> loadIdentity(final UpsertIdentityRequest request) {
        Optional<Identity> identity = dao.load(request.getProvidedAlias());
        if (identity.isPresent()) {
            log.debug("loaded identity {} from request {}", identity, request);
        }
        return identity;
    }

    private Identity createNewIdentity(final UpsertIdentityRequest request) {
        log.info("creating new identity from request {}", request);

        final Aliases loadedAliases = loadAliases(request);
        log.debug("loaded aliases {}", loadedAliases);

        final Alias idvId = idvIdGenerator.generate();
        log.debug("generated idv id {}", idvId);

        final Aliases allAliases = Aliases.with(idvId, request.getProvidedAlias()).add(loadedAliases);
        final Identity identity = Identity.withAliases(allAliases);
        log.info("saving identity {}", identity);
        dao.save(identity);
        return identity;
    }

    private Aliases loadAliases(final UpsertIdentityRequest request) {
        final AliasLoaderRequest loaderRequest = AliasLoaderRequest.builder()
                .channelId(request.getChannelId())
                .providedAlias(request.getProvidedAlias())
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
