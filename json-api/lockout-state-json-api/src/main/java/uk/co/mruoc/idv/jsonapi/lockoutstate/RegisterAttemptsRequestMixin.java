package uk.co.mruoc.idv.jsonapi.lockoutstate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.co.mruoc.idv.core.verificationattempts.model.DefaultRegisterAttemptsRequest;

@JsonDeserialize(as = DefaultRegisterAttemptsRequest.class)
public interface RegisterAttemptsRequestMixin {

    @JsonIgnore
    boolean isEmpty();

}
