package uk.co.mruoc.idv.core.identity.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class AliasLoadFailedExceptionTest {

    private final Alias alias = mock(Alias.class);

    @Test
    public void shouldCreateWithAlias() {
        final AliasLoadFailedException exception = new AliasLoadFailedException(alias);

        assertThat(exception.getCause()).isNull();
        assertThat(exception.getMessage()).isEqualTo(alias.toString());
        assertThat(exception.getAlias()).isEqualTo(alias);
    }

    @Test
    public void shouldCreateWithAliasAndCause() {
        final Throwable cause = new Exception("cause");
        final AliasLoadFailedException exception = new AliasLoadFailedException(alias, cause);

        assertThat(exception.getCause()).isEqualTo(cause);
        assertThat(exception.getMessage()).isEqualTo(alias.toString());
        assertThat(exception.getAlias()).isEqualTo(alias);
    }

}
