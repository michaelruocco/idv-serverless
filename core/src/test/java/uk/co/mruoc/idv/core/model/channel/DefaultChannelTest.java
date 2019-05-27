package uk.co.mruoc.idv.core.model.channel;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultChannelTest {

    private static final String ID = "type";

    private final Channel channel = new DefaultChannel(ID);

    @Test
    public void shouldReturnId() {
        final String id = channel.getId();

        assertThat(id).isEqualTo(ID);
    }

    @Test
    public void shouldPrintValues() {
        final String value = channel.toString();

        assertThat(value).isEqualTo("DefaultChannel(id=type)");
    }

}
