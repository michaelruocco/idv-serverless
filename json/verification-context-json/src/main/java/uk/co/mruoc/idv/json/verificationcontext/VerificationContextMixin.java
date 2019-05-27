package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface VerificationContextMixin {

    @JsonIgnore
    UUID getIdvId();

    @JsonIgnore
    String getChannelId();

    @JsonIgnore
    String getActivityType();

}
