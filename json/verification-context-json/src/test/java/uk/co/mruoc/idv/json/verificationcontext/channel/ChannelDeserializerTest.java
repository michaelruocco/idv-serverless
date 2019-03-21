package uk.co.mruoc.idv.json.verificationcontext.channel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextObjectMapperSingleton;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.*;

public class ChannelDeserializerTest {

    private static final String DEFAULT_CHANNEL_PATH = "/channel/default-channel.json";

    private static final ObjectMapper MAPPER = VerificationContextObjectMapperSingleton.get();

    @Test
    public void shouldSerializeDefaultChannel() throws JsonProcessingException, JSONException {
        final Channel channel = buildDefaultChannel();

        final String json = MAPPER.writeValueAsString(channel);

        final String expectedJson = loadContentFromClasspath(DEFAULT_CHANNEL_PATH);
        JSONAssert.assertEquals(expectedJson, json, JSONCompareMode.STRICT);
    }

    @Test
    public void shouldDeserializeDefaultChannel() throws IOException {
        final String json = loadContentFromClasspath(DEFAULT_CHANNEL_PATH);

        final Channel channel = MAPPER.readValue(json, Channel.class);

        final Channel expectedChannel = buildDefaultChannel();
        assertThat(channel).isEqualToComparingFieldByFieldRecursively(expectedChannel);
    }

    private static Channel buildDefaultChannel() {
        final String id = "DEFAULT_CHANNEL";
        return new DefaultChannel(id);
    }

}
