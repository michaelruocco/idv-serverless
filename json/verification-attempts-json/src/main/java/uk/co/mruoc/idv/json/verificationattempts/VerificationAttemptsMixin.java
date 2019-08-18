package uk.co.mruoc.idv.json.verificationattempts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@JsonPropertyOrder({ "lockoutStateId", "idvId", "idvIdAlias", "attempts" })
public interface VerificationAttemptsMixin {

    @JsonIgnore
    Instant getMostRecentTimestamp();

    @JsonIgnore
    Collection<UUID> getContextIds();

    @JsonProperty("attempts")
    Collection<VerificationAttempt> toCollection();

}
