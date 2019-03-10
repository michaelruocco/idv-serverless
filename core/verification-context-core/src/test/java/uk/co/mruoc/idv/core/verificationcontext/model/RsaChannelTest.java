package uk.co.mruoc.idv.core.verificationcontext.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RsaChannelTest {

    private final Channel channel = new RsaChannel();

    @Test
    public void shouldReturnId() {
        final String id = channel.getId();

        assertThat(id).isEqualTo(Channel.Ids.RSA);
    }

}
