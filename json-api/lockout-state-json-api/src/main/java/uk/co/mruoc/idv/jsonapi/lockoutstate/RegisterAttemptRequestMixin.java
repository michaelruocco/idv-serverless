package uk.co.mruoc.idv.jsonapi.lockoutstate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.co.mruoc.idv.core.verificationattempts.model.DefaultRegisterAttemptRequest;

@JsonDeserialize(as = DefaultRegisterAttemptRequest.class)
public interface RegisterAttemptRequestMixin {

    // intentionally blank
}
