package uk.co.mruoc.idv.json.verificationcontext.channel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.channel.model.Channel;
import uk.co.mruoc.idv.core.channel.model.DefaultChannel;

import java.io.IOException;

@Slf4j
public class ChannelDeserializer extends StdDeserializer<Channel> {

    public ChannelDeserializer() {
        super(Channel.class);
    }

    @Override
    public Channel deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode activityNode = parser.readValueAsTree();
        return toChannel(activityNode);
    }

    public Channel toChannel(final JsonNode activityNode) {
        return toDefaultChannel(activityNode);
    }

    private Channel toDefaultChannel(final JsonNode channelNode) {
        final String id = extractId(channelNode);
        return new DefaultChannel(id);
    }

    private static String extractId(final JsonNode channelNode) {
        return channelNode.get("id").asText();
    }

}
