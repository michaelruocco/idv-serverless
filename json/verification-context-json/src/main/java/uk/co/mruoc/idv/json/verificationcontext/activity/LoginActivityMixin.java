package uk.co.mruoc.idv.json.verificationcontext.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ "type", "timestamp" })
public interface LoginActivityMixin {

    // intentionally blank

}
