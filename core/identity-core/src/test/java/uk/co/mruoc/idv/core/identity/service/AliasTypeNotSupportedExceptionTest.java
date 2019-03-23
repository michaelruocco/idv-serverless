package uk.co.mruoc.idv.core.identity.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderService.AliasTypeNotSupportedException;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasTypeNotSupportedExceptionTest {

    private static final String ALIAS_TYPE_NAME = "aliasTypeName";
    private static final String CHANNEL_ID = "channelId";

    private final AliasTypeNotSupportedException exception = new AliasTypeNotSupportedException(ALIAS_TYPE_NAME, CHANNEL_ID);

    @Test
    public void shouldReturnMessage() {
        assertThat(exception.getMessage()).isEqualTo("alias type aliasTypeName is not supported for channel channelId");
    }

    @Test
    public void shouldReturnAliasTypeName() {
        assertThat(exception.getAliasTypeName()).isEqualTo(ALIAS_TYPE_NAME);
    }

    @Test
    public void shouldReturnChannelId() {
        assertThat(exception.getChannelId()).isEqualTo(CHANNEL_ID);
    }

}
