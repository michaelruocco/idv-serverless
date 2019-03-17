package uk.co.mruoc.idv.jsonapi.verificationcontext.channel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;
import uk.co.mruoc.idv.jsonapi.verificationcontext.JsonLoader;
import uk.co.mruoc.idv.jsonapi.verificationcontext.ObjectMapperSingleton;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ChannelDeserializerTest {

    private static final String DEFAULT_CHANNEL_PATH = "/channel/default-channel.json";

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    public void shouldSerializeDefaultChannel() throws JsonProcessingException {
        final Channel channel = buildDefaultChannel();

        final String json = MAPPER.writeValueAsString(channel);

        final String expectedJson = JsonLoader.loadJson(DEFAULT_CHANNEL_PATH);
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldDeserializeDefaultChannel() throws IOException {
        final String json = JsonLoader.loadJson(DEFAULT_CHANNEL_PATH);

        final Channel channel = MAPPER.readValue(json, Channel.class);

        final Channel expectedChannel = buildDefaultChannel();
        assertThat(channel).isEqualToComparingFieldByFieldRecursively(expectedChannel);
    }

    private static Channel buildDefaultChannel() {
        final String id = "DEFAULT_CHANNEL";
        return new DefaultChannel(id);
    }

}
