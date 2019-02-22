package uk.co.mruoc.idv.app.identity.model;

import lombok.ToString;
import uk.co.mruoc.idv.app.identity.model.alias.Alias;
import uk.co.mruoc.idv.app.identity.model.alias.AliasType;
import uk.co.mruoc.idv.app.identity.model.alias.IdvIdAlias;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableCollection;

@ToString
public class Identity {

    private final Collection<Alias> aliases;

    public static Identity withAliases(final Alias... aliases) {
        return withAliases(Arrays.asList(aliases));
    }

    public static Identity withAliases(final Collection<Alias> aliases) {
        final long count = countAliases(aliases, AliasType.IDV_ID);
        if (count == 1) {
            return new Identity(aliases);
        }
        throw new IdentityMustHaveExactlyOneIdvIdException();
    }

    private Identity(final Collection<Alias> aliases) {
        this.aliases = unmodifiableCollection(aliases);
    }

    public Collection<Alias> getAliases() {
        return aliases;
    }

    public Identity addAliases(final Alias... aliases) {
        return addAliases(Arrays.asList(aliases));
    }

    public Identity addAliases(final Collection<Alias> newAliases) {
        final Set<Alias> mergedAliases = new HashSet<>();
        mergedAliases.addAll(this.aliases);
        mergedAliases.addAll(newAliases);
        return new Identity(mergedAliases);
    }

    public Identity merge(final Identity identityToMerge) {
        Identity idvIdRemoved = identityToMerge.removeAliases(AliasType.IDV_ID);
        return addAliases(idvIdRemoved.aliases);
    }

    public Identity removeAliases(AliasType... typesToRemove) {
        return removeAliases(Arrays.asList(typesToRemove));
    }

    public Identity removeAliases(Collection<AliasType> typesToRemove) {
        final List<Alias> remainingAliases = aliases.stream()
                .filter(alias -> !typesToRemove.contains(alias.getType()))
                .collect(Collectors.toList());
        return new Identity(remainingAliases);
    }

    public boolean hasSameAliases(final Identity otherIdentity) {
        if (aliases.size() != otherIdentity.aliases.size()) {
            return false;
        }
        return otherIdentity.aliases.containsAll(aliases);
    }

    public boolean hasAlias(final AliasType type) {
        Optional<Alias> found = findAlias(type);
        return found.isPresent();
    }

    public IdvIdAlias getIdvId() {
        final Alias alias = getAlias(AliasType.IDV_ID);
        return (IdvIdAlias) alias;
    }

    public UUID getIdvIdValue() {
        final IdvIdAlias idvId = getIdvId();
        return idvId.getValueAsUuid();
    }

    public Alias getAlias(final AliasType type) {
        Optional<Alias> found = findAlias(type);
        return found.orElseThrow(() -> new IdentityAliasOfTypeNotFoundException(this, type));
    }

    private Optional<Alias> findAlias(final AliasType type) {
        return findAlias(aliases, type);
    }

    private static Optional<Alias> findAlias(final Collection<Alias> aliases, final AliasType type) {
        return filterByType(aliases, type).findFirst();
    }

    private static long countAliases(final Collection<Alias> aliases, final AliasType type) {
        return filterByType(aliases, type).count();
    }

    private static Stream<Alias> filterByType(final Collection<Alias> aliases, final AliasType type) {
        return aliases.stream().filter(alias -> alias.getType() == type);
    }

    public static class IdentityAliasOfTypeNotFoundException extends RuntimeException {

        private static final String MESSAGE = "%s does not have alias of type [%s]";

        public IdentityAliasOfTypeNotFoundException(final Identity identity, final AliasType aliasType) {
            super(String.format(MESSAGE, identity, aliasType));
        }

    }

    public static class IdentityMustHaveExactlyOneIdvIdException extends RuntimeException { }

}
