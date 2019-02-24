package uk.co.mruoc.idv.core.identity.model.alias;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasesTest {

    @Test
    public void shouldReturnStreamOfAliases() {
        final Alias idIdv = new IdvIdAlias();
        final Alias cardholderId = new UkcCardholderIdAlias("12345678");
        final Aliases aliases = Aliases.with(idIdv, cardholderId);

        final Stream<Alias> stream = aliases.stream();

        assertThat(stream.collect(Collectors.toList())).containsExactly(idIdv, cardholderId);
    }

}
