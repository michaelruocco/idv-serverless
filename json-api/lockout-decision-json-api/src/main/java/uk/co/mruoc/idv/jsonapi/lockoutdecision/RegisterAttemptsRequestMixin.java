package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultRegisterAttemptsRequest;

@JsonDeserialize(as = DefaultRegisterAttemptsRequest.class)
public interface RegisterAttemptsRequestMixin {

    // intentionally blank
}
