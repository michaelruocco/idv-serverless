package uk.co.mruoc.idv.jsonapi.verificationcontext.result;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface JsonApiVerificationMethodResultsMixin {

    @JsonIgnore
    UUID getId();
    
}
