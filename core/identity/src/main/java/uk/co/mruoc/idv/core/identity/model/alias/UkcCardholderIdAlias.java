package uk.co.mruoc.idv.core.identity.model.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UkcCardholderIdAlias extends AbstractAlias {

    public UkcCardholderIdAlias(final String value) {
        super(AliasType.UKC_CARDHOLDER_ID, AliasFormat.PLAIN_TEXT, value);
    }

    @Override
    public boolean isCardNumber() {
        return false;
    }

}
