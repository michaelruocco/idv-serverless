package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface LockoutStateMixin {

    @JsonIgnore
    UUID getId();

    @JsonIgnore
    UUID getIdvId();

    @JsonIgnore
    int getNumberOfAttempts();

    @JsonIgnore
    boolean isTimeBased();

    @JsonIgnore
    VerificationAttempts getVerificationAttempts();

}
