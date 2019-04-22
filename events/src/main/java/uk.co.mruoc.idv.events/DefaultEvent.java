package uk.co.mruoc.idv.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class DefaultEvent implements Event {

    private final String type;
    private final Instant timestamp;
    private final Object data;

}
