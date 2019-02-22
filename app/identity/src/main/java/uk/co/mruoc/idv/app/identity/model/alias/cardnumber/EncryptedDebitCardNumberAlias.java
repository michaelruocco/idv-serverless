package uk.co.mruoc.idv.app.identity.model.alias.cardnumber;

public class EncryptedDebitCardNumberAlias extends DebitCardNumberAlias {

    public EncryptedDebitCardNumberAlias(final String value) {
        super(SecureAliasFormat.ENCRYPTED, value);
    }

}
