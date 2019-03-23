package uk.co.mruoc.idv.core.identity.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.service.IdentityService.IdentityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class IdentityNotFoundExceptionTest {

    private final Alias alias = mock(Alias.class);

    private final IdentityNotFoundException exception = new IdentityNotFoundException(alias);

    @Test
    public void shouldReturnMessage() {
        assertThat(exception.getMessage()).isEqualTo(alias.toString());
    }

    @Test
    public void shouldReturnAlias() {
        assertThat(exception.getAlias()).isEqualTo(alias);
    }

}
