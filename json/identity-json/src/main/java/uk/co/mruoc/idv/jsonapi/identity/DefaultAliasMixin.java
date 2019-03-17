package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "type", "format", "value" })
public abstract class DefaultAliasMixin {

    @JsonProperty("type")
    public abstract String getTypeName();

    @JsonIgnore
    public abstract boolean isCardNumber();

    @JsonIgnore
    public abstract boolean isSensitive();

}
