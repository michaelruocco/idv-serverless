package uk.co.mruoc.idv.events.sns;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.events.Event;
import uk.co.mruoc.idv.events.EventPublisher;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FakeEventPublisher implements EventPublisher {

    private final List<Event> events = new ArrayList<>();

    @Override
    public void publish(final Event event) {
        log.info("handling event {}", event);
        events.add(event);
    }

}
