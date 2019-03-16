package uk.co.mruoc.idv.jsonapi.verificationcontext.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "type", "timestamp", "properties" })
public abstract class OnlinePurchaseActivityMixin {

    @JsonIgnore
    public abstract String getMerchant();

    @JsonIgnore
    public abstract String getReference();

    @JsonIgnore
    public abstract String getCost();

}
