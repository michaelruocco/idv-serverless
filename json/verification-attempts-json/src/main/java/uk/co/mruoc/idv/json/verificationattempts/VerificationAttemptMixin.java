package uk.co.mruoc.idv.json.verificationattempts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;

@JsonDeserialize(as = VerificationAttempt.class)
public interface VerificationAttemptMixin {

    @JsonIgnore
    String getAliasTypeName();

}
