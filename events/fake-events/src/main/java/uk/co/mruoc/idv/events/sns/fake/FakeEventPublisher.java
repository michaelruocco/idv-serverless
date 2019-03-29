package uk.co.mruoc.idv.events.sns.fake;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.events.Event;
import uk.co.mruoc.idv.events.EventPublisher;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class FakeEventPublisher implements EventPublisher {

    private final Collection<Event> events = new ArrayList<>();

    @Override
    public void publish(final Event event) {
        log.info("handling event {}", event);
        events.add(event);
    }

    public Collection<Event> getPublishedEvents() {
        return events;
    }

}
