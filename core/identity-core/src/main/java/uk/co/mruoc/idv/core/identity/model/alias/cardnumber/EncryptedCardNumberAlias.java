package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

public class EncryptedCardNumberAlias extends CardNumberAlias {

    public EncryptedCardNumberAlias(final String value) {
        super(Formats.ENCRYPTED, value);
    }

}
