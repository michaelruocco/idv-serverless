package uk.co.mruoc.idv.core.lockoutdecision.model;

import java.util.UUID;

public interface LockoutState {

    UUID getId();

    UUID getIdvId();

    String getType();

    boolean isLocked();

    int getNumberOfAttempts();

    boolean isTimeBased();

}
