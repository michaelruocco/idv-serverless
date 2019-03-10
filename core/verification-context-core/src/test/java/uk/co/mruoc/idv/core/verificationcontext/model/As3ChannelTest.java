package uk.co.mruoc.idv.core.verificationcontext.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class As3ChannelTest {

    private final Channel channel = new As3Channel();

    @Test
    public void shouldReturnId() {
        final String id = channel.getId();

        assertThat(id).isEqualTo(Channel.Ids.AS3);
    }

}
