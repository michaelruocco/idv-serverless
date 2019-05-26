package uk.co.mruoc.idv.core.identity.model.alias;

import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.CardNumberAliasType;

import java.util.Arrays;
import java.util.Collection;

public interface AliasType {

    Collection<String> CARD_NUMBER_TYPES = Arrays.asList(
            Names.CREDIT_CARD_NUMBER,
            Names.DEBIT_CARD_NUMBER,
            Names.CARD_NUMBER
    );

    String name();

    boolean isSensitive();

    boolean isCardNumber();

    static AliasType toAliasType(final String type) {
        if (AliasType.isCardNumber(type)) {
            return new CardNumberAliasType(type);
        }
        return new DefaultAliasType(type);
    }

    static boolean isCardNumber(final String type) {
        return CARD_NUMBER_TYPES.contains(type);
    }

    static boolean isIdvId(final String type) {
        return Names.IDV_ID.equals(type);
    }

    interface Names {

        String IDV_ID = "IDV_ID";
        String CREDIT_CARD_NUMBER = "CREDIT_CARD_NUMBER";
        String DEBIT_CARD_NUMBER = "DEBIT_CARD_NUMBER";
        String CARD_NUMBER = "CARD_NUMBER";

    }

}
