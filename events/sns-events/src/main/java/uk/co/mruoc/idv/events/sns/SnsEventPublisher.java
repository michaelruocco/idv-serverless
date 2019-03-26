package uk.co.mruoc.idv.events.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.events.Event;
import uk.co.mruoc.idv.events.EventPublisher;

import java.io.UncheckedIOException;

@Slf4j
public class SnsEventPublisher implements EventPublisher {

    private final AmazonSNS sns;
    private final String topicArn;
    private final ObjectMapper mapper;

    public SnsEventPublisher(final AmazonSNS sns, final String topicArn, final ObjectMapper mapper) {
        this.sns = sns;
        this.topicArn = topicArn;
        this.mapper = mapper;
    }

    @Override
    public void publish(final Event event) {
        log.info("handling event {}", event);
        final PublishRequest request = toPublishRequest(event);
        log.info("publishing sns request {}", request);
        sns.publish(request);
    }

    private PublishRequest toPublishRequest(final Event event) {
        try {
            final String json = mapper.writeValueAsString(event);
            return new PublishRequest()
                    .withTopicArn(topicArn)
                    .withMessage(json);
        } catch (final JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

}
