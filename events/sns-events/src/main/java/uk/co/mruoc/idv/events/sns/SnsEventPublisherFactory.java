package uk.co.mruoc.idv.events.sns;

import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.idv.events.EventPublisher;

public class SnsEventPublisherFactory {

    private final SnsEventEnvironment environment;
    private final ObjectMapper mapper;

    public SnsEventPublisherFactory(final SnsEventEnvironment environment, final ObjectMapper mapper) {
        this.environment = environment;
        this.mapper = mapper;
    }

    public EventPublisher build() {
        final AmazonSNSAsync sns = AmazonSNSAsyncClientBuilder.standard()
                .withRegion(environment.getRegion())
                .build();
        return new SnsEventPublisher(sns, environment.getEventTopicArn(), mapper);
    }

}
