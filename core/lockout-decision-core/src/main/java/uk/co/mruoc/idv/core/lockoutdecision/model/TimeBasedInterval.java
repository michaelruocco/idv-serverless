package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
public class TimeBasedInterval {

    private final int numberOfAttempts;
    private final Duration duration;

}
