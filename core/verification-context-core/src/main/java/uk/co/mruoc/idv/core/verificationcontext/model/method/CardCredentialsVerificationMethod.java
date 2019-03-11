package uk.co.mruoc.idv.core.verificationcontext.model.method;

public class CardCredentialsVerificationMethod extends DefaultVerificationMethod {

    public CardCredentialsVerificationMethod(final int duration) {
        super(Names.CARD_CREDENTIALS, duration);
    }

}
