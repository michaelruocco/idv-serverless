package uk.co.mruoc.idv.dao.identity;

import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.service.IdentityDao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeIdentityDao implements IdentityDao {

    private final Map<Alias, Identity> identities = new HashMap<>();

    @Override
    public void save(final Identity identity) {
        final Aliases aliases = identity.getAliases();
        aliases.stream().forEach(alias -> identities.put(alias, identity));
    }

    @Override
    public void delete(final Identity identity) {
        final Aliases aliases = identity.getAliases();
        aliases.stream().forEach(identities::remove);
    }

    @Override
    public Optional<Identity> load(final Alias alias) {
        return Optional.ofNullable(identities.get(alias));
    }

}
