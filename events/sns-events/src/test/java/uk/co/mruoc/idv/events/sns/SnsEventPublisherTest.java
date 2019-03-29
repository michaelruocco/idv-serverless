package uk.co.mruoc.idv.events.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.events.Event;
import uk.co.mruoc.idv.events.EventPublisher;
import uk.co.mruoc.idv.json.JsonConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SnsEventPublisherTest {

    private final AmazonSNS sns = mock(AmazonSNS.class);
    private final String topicArn = "topicArn";
    private final JsonConverter converter = mock(JsonConverter.class);

    private final EventPublisher publisher = new SnsEventPublisher(sns, topicArn, converter);

    @Test
    public void shouldPublishEventAsJson() {
        final Event event = mock(Event.class);
        final String expectedMessage = "expectedMessage";
        given(converter.toJson(event)).willReturn(expectedMessage);

        publisher.publish(event);

        final ArgumentCaptor<PublishRequest> captor = ArgumentCaptor.forClass(PublishRequest.class);
        verify(sns).publish(captor.capture());
        final PublishRequest request = captor.getValue();
        assertThat(request.getTopicArn()).isEqualTo(topicArn);
        assertThat(request.getMessage()).isEqualTo(expectedMessage);
    }

}
