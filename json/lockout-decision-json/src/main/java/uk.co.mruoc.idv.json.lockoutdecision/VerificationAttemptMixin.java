package uk.co.mruoc.idv.json.lockoutdecision;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface VerificationAttemptMixin {

    @JsonIgnore
    String getAliasTypeName();

}
