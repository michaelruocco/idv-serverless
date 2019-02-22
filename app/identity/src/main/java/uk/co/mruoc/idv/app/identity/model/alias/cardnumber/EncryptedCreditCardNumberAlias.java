package uk.co.mruoc.idv.app.identity.model.alias.cardnumber;

public class EncryptedCreditCardNumberAlias extends CreditCardNumberAlias {

    public EncryptedCreditCardNumberAlias(final String value) {
        super(SecureAliasFormat.ENCRYPTED, value);
    }

}
