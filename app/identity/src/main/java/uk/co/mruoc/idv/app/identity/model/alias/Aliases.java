package uk.co.mruoc.idv.app.identity.model.alias;

import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableCollection;

@ToString
public class Aliases implements Iterable<Alias> {

    private final Collection<Alias> aliases;

    public static Aliases with(final Alias... aliases) {
        return with(Arrays.asList(aliases));
    }

    public static Aliases with(final Collection<Alias> aliases) {
        return new Aliases(aliases);
    }

    private Aliases(final Collection<Alias> aliases) {
        this.aliases = unmodifiableCollection(aliases);
    }

    @Override
    public Iterator<Alias> iterator() {
        return aliases.iterator();
    }

    public Aliases add(final Aliases aliases) {
        return add(aliases.aliases);
    }

    public Aliases removeByType(final AliasType... typesToRemove) {
        return removeByType(Arrays.asList(typesToRemove));
    }

    public Aliases removeByType(final Collection<AliasType> typesToRemove) {
        final List<Alias> remainingAliases = aliases.stream()
                .filter(alias -> !typesToRemove.contains(alias.getType()))
                .collect(Collectors.toList());
        return Aliases.with(remainingAliases);
    }

    public boolean containsSame(final Aliases otherAliases) {
        if (aliases.size() != otherAliases.size()) {
            return false;
        }
        return otherAliases.aliases.containsAll(this.aliases);
    }

    public boolean hasType(final AliasType type) {
        final long count = countByType(type);
        return count > 0;
    }

    public long countByType(final AliasType type) {
        return filterByType(type).count();
    }

    public Collection<Alias> findByType(final AliasType type) {
        return filterByType(type).collect(Collectors.toList());
    }

    private int size() {
        return aliases.size();
    }

    private Aliases add(final Collection<Alias> newAliases) {
        final Set<Alias> mergedAliases = new HashSet<>();
        mergedAliases.addAll(this.aliases);
        mergedAliases.addAll(newAliases);
        return Aliases.with(mergedAliases);
    }

    private Stream<Alias> filterByType(final AliasType type) {
        return aliases.stream().filter(alias -> alias.getType() == type);
    }

}
