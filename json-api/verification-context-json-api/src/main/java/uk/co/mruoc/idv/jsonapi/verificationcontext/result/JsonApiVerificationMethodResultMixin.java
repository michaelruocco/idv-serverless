package uk.co.mruoc.idv.jsonapi.verificationcontext.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.mruoc.idv.json.verificationcontext.result.VerificationMethodResultMixin;

import java.util.UUID;

public interface JsonApiVerificationMethodResultMixin extends VerificationMethodResultMixin {

    @JsonIgnore
    UUID getId();
    
    @JsonIgnore
    UUID getContextId();

}
