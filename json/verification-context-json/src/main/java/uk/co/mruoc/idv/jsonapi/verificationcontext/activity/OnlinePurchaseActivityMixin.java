package uk.co.mruoc.idv.jsonapi.verificationcontext.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "type", "timestamp", "properties" })
public interface OnlinePurchaseActivityMixin {

    @JsonIgnore
    String getMerchant();

    @JsonIgnore
    String getReference();

    @JsonIgnore
    String getCost();

}
