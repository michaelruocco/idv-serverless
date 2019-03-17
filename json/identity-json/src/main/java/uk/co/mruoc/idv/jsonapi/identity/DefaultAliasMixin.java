package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "type", "format", "value" })
public interface DefaultAliasMixin {

    @JsonProperty("type")
    String getTypeName();

    @JsonIgnore
    boolean isCardNumber();

    @JsonIgnore
    boolean isSensitive();

}
