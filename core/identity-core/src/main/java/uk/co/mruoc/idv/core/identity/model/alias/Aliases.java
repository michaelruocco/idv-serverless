package uk.co.mruoc.idv.core.identity.model.alias;

import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableSet;

@ToString
public class Aliases implements Iterable<Alias> {

    private final Set<Alias> aliases;

    public static Aliases empty() {
        return with();
    }

    public static Aliases with(final Aliases aliases) {
        return with(aliases.aliases);
    }

    public static Aliases with(final Alias... aliases) {
        return with(Arrays.asList(aliases));
    }

    public static Aliases with(final Collection<Alias> aliases) {
        return new Aliases(aliases);
    }

    private Aliases(final Collection<Alias> aliases) {
        this.aliases = unmodifiableSet(new LinkedHashSet<>(aliases));
    }

    @Override
    public Iterator<Alias> iterator() {
        return aliases.iterator();
    }

    public Aliases add(final Alias alias) {
        return add(singleton(alias));
    }

    public Aliases add(final Aliases aliases) {
        return add(aliases.aliases);
    }

    public Aliases removeByTypeName(final String... typesNamesToRemove) {
        return removeByTypeNames(Arrays.asList(typesNamesToRemove));
    }

    public Aliases removeByTypeNames(final Collection<String> typeNamesToRemove) {
        final List<Alias> remainingAliases = aliases.stream()
                .filter(alias -> !typeNamesToRemove.contains(alias.getTypeName()))
                .collect(Collectors.toList());
        return Aliases.with(remainingAliases);
    }

    public boolean containsExactly(final Aliases otherAliases) {
        if (aliases.size() != otherAliases.size()) {
            return false;
        }
        return otherAliases.aliases.containsAll(this.aliases);
    }

    public boolean hasType(final String typeName) {
        final long count = countByType(typeName);
        return count > 0;
    }

    public long countByType(final String typeName) {
        return filterByTypeName(typeName).count();
    }

    public Collection<Alias> getByTypeName(final String typeName) {
        return getByTypeNames(singleton(typeName));
    }

    public Collection<Alias> getByTypeNames(final Collection<String> typeNames) {
        return filterByTypeNames(typeNames).collect(Collectors.toList());
    }

    public Stream<Alias> stream() {
        return aliases.stream();
    }

    public int size() {
        return aliases.size();
    }

    public Collection<Alias> toCollection() {
        return aliases;
    }

    private Aliases add(final Collection<Alias> newAliases) {
        final Set<Alias> mergedAliases = new HashSet<>();
        mergedAliases.addAll(this.aliases);
        mergedAliases.addAll(newAliases);
        return Aliases.with(mergedAliases);
    }

    private Stream<Alias> filterByTypeName(final String typeName) {
        return filterByTypeNames(singleton(typeName));
    }

    private Stream<Alias> filterByTypeNames(final Collection<String> typeNames) {
        return aliases.stream().filter(alias -> typeNames.contains(alias.getTypeName()));
    }

}
