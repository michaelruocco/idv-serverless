package uk.co.mruoc.idv.plugin.uk.verificationcontext.channel;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;

import static org.assertj.core.api.Assertions.assertThat;

public class As3ChannelTest {

    private final Channel channel = new As3Channel();

    @Test
    public void shouldReturnId() {
        final String id = channel.getId();

        assertThat(id).isEqualTo(UkChannel.Ids.AS3);
    }

}
