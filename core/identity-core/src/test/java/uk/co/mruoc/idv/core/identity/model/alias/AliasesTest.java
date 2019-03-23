package uk.co.mruoc.idv.core.identity.model.alias;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class AliasesTest {

    private final Alias idIdv = new IdvIdAlias();
    private final Alias alias = mock(Alias.class);

    @Test
    public void shouldReturnStreamOfAliases() {
        final Aliases aliases = Aliases.with(idIdv, alias);

        final Stream<Alias> stream = aliases.stream();

        assertThat(stream.collect(Collectors.toList())).containsExactly(idIdv, alias);
    }

    @Test
    public void shouldReturnSize() {
        final Aliases aliases = Aliases.with(idIdv, alias);

        final int size = aliases.size();

        assertThat(size).isEqualTo(2);
    }

    @Test
    public void shouldAddAlias() {
        final Aliases aliases = Aliases.with(idIdv);

        final Aliases addedAliases = aliases.add(alias);

        assertThat(addedAliases).containsExactlyInAnyOrder(idIdv, alias);
    }

    @Test
    public void shouldCreateAliasesFromOtherAliases() {
        final Aliases aliases = Aliases.with(idIdv, alias);

        final Aliases duplicateAliases = Aliases.with(aliases);

        assertThat(duplicateAliases.containsExactly(aliases)).isTrue();
    }

}
