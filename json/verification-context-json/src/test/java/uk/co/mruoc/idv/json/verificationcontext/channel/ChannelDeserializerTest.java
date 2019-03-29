package uk.co.mruoc.idv.json.verificationcontext.channel;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextJsonConverterFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class ChannelDeserializerTest {

    private static final String DEFAULT_CHANNEL_PATH = "/channel/default-channel.json";

    private static final JsonConverter CONVERTER = new VerificationContextJsonConverterFactory().build();

    @Test
    public void shouldSerializeDefaultChannel() throws JSONException {
        final Channel channel = buildDefaultChannel();

        final String json = CONVERTER.toJson(channel);

        final String expectedJson = loadContentFromClasspath(DEFAULT_CHANNEL_PATH);
        JSONAssert.assertEquals(expectedJson, json, JSONCompareMode.STRICT);
    }

    @Test
    public void shouldDeserializeDefaultChannel() {
        final String json = loadContentFromClasspath(DEFAULT_CHANNEL_PATH);

        final Channel channel = CONVERTER.toObject(json, Channel.class);

        final Channel expectedChannel = buildDefaultChannel();
        assertThat(channel).isEqualToComparingFieldByFieldRecursively(expectedChannel);
    }

    private static Channel buildDefaultChannel() {
        final String id = "DEFAULT_CHANNEL";
        return new DefaultChannel(id);
    }

}
