package uk.co.mruoc.idv.events.sns;

import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.events.EventPublisher;
import uk.co.mruoc.idv.json.JsonConverter;

public class SnsEventPublisherFactory {

    private final SnsEventEnvironment environment;
    private final JsonConverter converter;

    public SnsEventPublisherFactory(final SnsEventEnvironment environment, final JsonConverter converter) {
        this.environment = environment;
        this.converter = converter;
    }

    public EventPublisher build() {
        final AmazonSNSAsync sns = AmazonSNSAsyncClientBuilder.standard()
                .withRegion(environment.getRegion())
                .build();
        return new SnsEventPublisher(sns, environment.getEventTopicArn(), converter);
    }

}
