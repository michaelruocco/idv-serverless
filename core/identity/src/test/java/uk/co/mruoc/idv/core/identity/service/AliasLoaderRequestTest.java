package uk.co.mruoc.idv.core.identity.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasLoaderRequestTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final Aliases ALIASES = Aliases.empty();

    private final AliasLoaderRequest request = new AliasLoaderRequest(CHANNEL_ID, ALIASES);

    @Test
    public void shouldReturnChannelId() {
        assertThat(request.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    public void shouldReturnAliases() {
        assertThat(request.getAliases()).isEqualTo(ALIASES);
    }

}
