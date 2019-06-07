package uk.co.mruoc.idv.json.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Instant;

@JsonPropertyOrder({ "lockoutStateId", "idvId", "idvIdAlias", "attempts" })
public interface VerificationAttemptsMixin {

    @JsonIgnore
    Instant getMostRecentTimestamp();

}
