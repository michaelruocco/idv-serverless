package uk.co.mruoc.idv.events.sns.fake;

import org.junit.Test;
import uk.co.mruoc.idv.events.Event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class FakeEventPublisherTest {

    private final FakeEventPublisher publisher = new FakeEventPublisher();

    @Test
    public void shouldRecordPublishedEvents() {
        final Event event1 = mock(Event.class);
        final Event event2 = mock(Event.class);

        publisher.publish(event1);
        publisher.publish(event2);

        assertThat(publisher.getPublishedEvents()).containsExactly(event1, event2);
    }

}
