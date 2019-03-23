package uk.co.mruoc.idv.plugin.uk.identity.model;

import uk.co.mruoc.idv.core.identity.model.alias.AliasType;

public interface UkAliasType extends AliasType {

    static AliasType toAliasType(final String type) {
        return AliasType.toAliasType(type);
    }

    interface Names extends AliasType.Names {

        String UKC_CARDHOLDER_ID = "UKC_CARDHOLDER_ID";
        String BUK_CUSTOMER_ID = "BUK_CUSTOMER_ID";

    }

}
