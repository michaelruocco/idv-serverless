package uk.co.mruoc.idv.json.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;

@JsonDeserialize(as = VerificationAttempt.class)
public interface VerificationAttemptMixin {

    @JsonIgnore
    String getAliasTypeName();

}
