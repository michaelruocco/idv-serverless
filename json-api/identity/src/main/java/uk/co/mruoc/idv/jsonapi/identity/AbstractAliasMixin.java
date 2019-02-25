package uk.co.mruoc.idv.jsonapi.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "type", "format", "value" })
public abstract class AbstractAliasMixin {

    @JsonIgnore
    public abstract boolean isCardNumber();

    @JsonIgnore
    public abstract boolean isSensitive();

}
