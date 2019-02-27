package uk.co.mruoc.idv.core.identity.model.alias.cardnumber;

public class EncryptedDebitCardNumberAlias extends DebitCardNumberAlias {

    public EncryptedDebitCardNumberAlias(final String value) {
        super(Formats.ENCRYPTED, value);
    }

}
