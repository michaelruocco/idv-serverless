package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultRegisterAttemptRequest;

@JsonDeserialize(as = DefaultRegisterAttemptRequest.class)
public interface RegisterAttemptRequestMixin {

    // intentionally blank
}
