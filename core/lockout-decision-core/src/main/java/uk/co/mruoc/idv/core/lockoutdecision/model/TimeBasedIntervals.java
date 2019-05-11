package uk.co.mruoc.idv.core.lockoutdecision.model;

import java.util.Optional;

public interface TimeBasedIntervals {

    Optional<TimeBasedInterval> getInternalFor(int numberOfAttempts);

    String getType();

}
