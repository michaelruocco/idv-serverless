package uk.co.mruoc.idv.json.verificationcontext.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface VerificationMethodResultsMixin {

    @JsonIgnore
    boolean isEmpty();

}
