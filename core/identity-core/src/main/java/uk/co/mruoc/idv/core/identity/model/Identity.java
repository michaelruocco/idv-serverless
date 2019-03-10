package uk.co.mruoc.idv.core.identity.model;

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

    private static final String IDV_ID_ALIAS_TYPE = AliasType.Names.IDV_ID;

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
        final Aliases remainingAliases = identityToMerge.aliases.removeByTypeName(AliasType.Names.IDV_ID);
        return addAliases(remainingAliases);
    }

    public Identity removeAliases(String... typeNamesToRemove) {
        return removeAliases(Arrays.asList(typeNamesToRemove));
    }

    public Identity removeAliases(Collection<String> typeNamesToRemove) {
        final Aliases remainingAliases = aliases.removeByTypeNames(typeNamesToRemove);
        return Identity.withAliases(remainingAliases);
    }

    public boolean hasSameAliases(final Identity otherIdentity) {
        return aliases.containsExactly(otherIdentity.aliases);
    }

    public boolean hasAlias(final String typeName) {
        return aliases.hasType(typeName);
    }

    public IdvIdAlias getIdvId() {
        Collection<Alias> aliases = getAliasesByType(IDV_ID_ALIAS_TYPE);
        Alias alias = aliases.iterator().next();
        return new IdvIdAlias(alias.getValue());
    }

    public UUID getIdvIdValue() {
        final IdvIdAlias idvId = getIdvId();
        return idvId.getValueAsUuid();
    }

    public Collection<Alias> getAliasesByType(final String typeName) {
        return aliases.getByTypeName(typeName);
    }

    private static void validate(final Aliases aliases) {
        if (aliases.countByType(IDV_ID_ALIAS_TYPE) != 1) {
            throw new IdentityMustHaveExactlyOneIdvIdException();
        }
    }

    public int getAliasCount() {
        return aliases.size();
    }

    public static class IdentityMustHaveExactlyOneIdvIdException extends RuntimeException {

        // intentionally blank

    }

}
