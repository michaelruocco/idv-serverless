package uk.co.mruoc.idv.json.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public interface VerificationAttemptsMixin {

    @JsonIgnore
    Instant getMostRecentTimestamp();

}
