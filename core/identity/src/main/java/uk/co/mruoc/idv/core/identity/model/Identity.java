package uk.co.mruoc.idv.core.identity.model;

import lombok.NoArgsConstructor;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@ToString
public class Identity {

    private final Aliases aliases;

    public static Identity withAliases(final Alias... aliases) {
        return withAliases(Arrays.asList(aliases));
    }

    public static Identity withAliases(final Collection<Alias> aliasCollection) {
        return withAliases(Aliases.with(aliasCollection));
    }

    public static Identity withAliases(final Aliases aliases) {
        validate(aliases);
        return new Identity(aliases);
    }

    private Identity(final Aliases aliases) {
        this.aliases = aliases;
    }

    public Aliases getAliases() {
        return aliases;
    }

    public Identity addAliases(final Alias... aliasesToAdd) {
        return addAliases(Aliases.with(aliasesToAdd));
    }

    public Identity addAliases(final Aliases aliasesToAdd) {
        final Aliases newAliases = aliases.add(aliasesToAdd);
        return Identity.withAliases(newAliases);
    }

    public Identity merge(final Identity identityToMerge) {
        final Aliases remainingAliases = identityToMerge.aliases.removeByType(AliasType.IDV_ID);
        return addAliases(remainingAliases);
    }

    public Identity removeAliases(AliasType... typesToRemove) {
        return removeAliases(Arrays.asList(typesToRemove));
    }

    public Identity removeAliases(Collection<AliasType> typesToRemove) {
        final Aliases remainingAliases = aliases.removeByType(typesToRemove);
        return Identity.withAliases(remainingAliases);
    }

    public boolean hasSameAliases(final Identity otherIdentity) {
        return aliases.containsExactly(otherIdentity.aliases);
    }

    public boolean hasAlias(final AliasType type) {
        return aliases.hasType(type);
    }

    public IdvIdAlias getIdvId() {
        Collection<Alias> aliases = getAliasesByType(AliasType.IDV_ID);
        return (IdvIdAlias) aliases.iterator().next();
    }

    public UUID getIdvIdValue() {
        final IdvIdAlias idvId = getIdvId();
        return idvId.getValueAsUuid();
    }

    public Collection<Alias> getAliasesByType(final AliasType type) {
        return aliases.getByType(type);
    }

    private static void validate(final Aliases aliases) {
        if (aliases.countByType(AliasType.IDV_ID) != 1) {
            throw new IdentityMustHaveExactlyOneIdvIdException();
        }
    }

    public static class IdentityMustHaveExactlyOneIdvIdException extends RuntimeException {

        // intentionally blank

    }

}
