package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

public class EncryptedCreditCardNumberAlias extends CreditCardNumberAlias {

    public EncryptedCreditCardNumberAlias(final String value) {
        super(Formats.ENCRYPTED, value);
    }

}
