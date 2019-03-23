package uk.co.mruoc.idv.core.identity.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class AliasLoaderRequestTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final Alias PROVIDED_ALIAS = mock(Alias.class);

    private final AliasLoaderRequest request = new AliasLoaderRequest(CHANNEL_ID, PROVIDED_ALIAS);

    @Test
    public void shouldReturnChannelId() {
        assertThat(request.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    public void shouldReturnProvidedAlias() {
        assertThat(request.getProvidedAlias()).isEqualTo(PROVIDED_ALIAS);
    }

}
