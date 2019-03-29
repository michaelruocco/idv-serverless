package uk.co.mruoc.idv.events.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import uk.co.mruoc.idv.events.EventPublisher;
import uk.co.mruoc.idv.json.JsonConverter;

public class SnsEventPublisherFactory {

    private final SnsEventEnvironment environment;
    private final JsonConverter converter;
    private final AmazonSNS sns;

    public SnsEventPublisherFactory(final SnsEventEnvironment environment, final JsonConverter converter) {
        this(environment, converter, buildClient(environment));
    }

    public SnsEventPublisherFactory(final SnsEventEnvironment environment, final JsonConverter converter,  final AmazonSNS sns) {
        this.environment = environment;
        this.converter = converter;
        this.sns = sns;
    }

    public EventPublisher build() {
        return new SnsEventPublisher(sns, environment.getEventTopicArn(), converter);
    }

    private static AmazonSNS buildClient(final SnsEventEnvironment environment) {
        return AmazonSNSAsyncClientBuilder.standard()
                .withRegion(environment.getRegion())
                .build();
    }

}
