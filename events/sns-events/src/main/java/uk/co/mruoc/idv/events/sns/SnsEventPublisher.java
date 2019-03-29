package uk.co.mruoc.idv.events.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.events.Event;
import uk.co.mruoc.idv.events.EventPublisher;
import uk.co.mruoc.idv.json.JsonConverter;

@Slf4j
public class SnsEventPublisher implements EventPublisher {

    private final AmazonSNS sns;
    private final String topicArn;
    private final JsonConverter converter;

    public SnsEventPublisher(final AmazonSNS sns, final String topicArn, final JsonConverter converter) {
        this.sns = sns;
        this.topicArn = topicArn;
        this.converter = converter;
    }

    @Override
    public void publish(final Event event) {
        log.info("handling event {}", event);
        final PublishRequest request = toPublishRequest(event);
        log.info("publishing sns request {}", request);
        sns.publish(request);
    }

    private PublishRequest toPublishRequest(final Event event) {
        final String json = converter.toJson(event);
        return new PublishRequest()
                .withTopicArn(topicArn)
                .withMessage(json);
    }

}
