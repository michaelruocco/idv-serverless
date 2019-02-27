package uk.co.mruoc.idv.core.identity.service;

import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import java.util.Optional;

public interface IdentityDao {

    void save(final Identity identity);

    Optional<Identity> load(final Alias alias);

    void delete(final Identity identity);

}
