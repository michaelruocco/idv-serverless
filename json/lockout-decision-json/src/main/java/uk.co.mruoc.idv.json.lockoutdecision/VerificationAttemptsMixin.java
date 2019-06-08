package uk.co.mruoc.idv.json.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;

import java.time.Instant;
import java.util.Collection;

@JsonPropertyOrder({ "lockoutStateId", "idvId", "idvIdAlias", "attempts" })
public interface VerificationAttemptsMixin {

    @JsonIgnore
    Instant getMostRecentTimestamp();

    @JsonProperty("attempts")
    Collection<VerificationAttempt> toCollection();

}
