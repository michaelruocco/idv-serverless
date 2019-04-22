package uk.co.mruoc.idv.json.verificationcontext.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface VerificationMethodResultMixin {

    @JsonIgnore
    boolean isSuccessful();

    @JsonProperty("method")
    String getMethodName();

    @JsonProperty("sequence")
    String getSequenceName();

}
