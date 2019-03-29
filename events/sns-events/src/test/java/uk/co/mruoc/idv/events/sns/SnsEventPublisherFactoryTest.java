package uk.co.mruoc.idv.events.sns;

import com.amazonaws.services.sns.AmazonSNS;
import org.junit.Test;
import uk.co.mruoc.idv.events.EventPublisher;
import uk.co.mruoc.idv.json.JsonConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class SnsEventPublisherFactoryTest {

    private final SnsEventEnvironment environment = mock(SnsEventEnvironment.class);
    private final JsonConverter converter = mock(JsonConverter.class);
    private final AmazonSNS sns = mock(AmazonSNS.class);

    private final SnsEventPublisherFactory factory = new SnsEventPublisherFactory(environment, converter, sns);

    @Test
    public void shouldBuildEventPublisher() {
        final EventPublisher publisher = factory.build();

        assertThat(publisher).isNotNull();
    }

}
