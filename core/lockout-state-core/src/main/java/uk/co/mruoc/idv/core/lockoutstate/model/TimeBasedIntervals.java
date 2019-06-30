package uk.co.mruoc.idv.core.lockoutstate.model;

import java.util.Optional;

public interface TimeBasedIntervals {

    Optional<TimeBasedInterval> getInternalFor(int numberOfAttempts);

    String getType();

}
