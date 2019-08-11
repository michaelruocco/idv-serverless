package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.ToString;

@ToString(callSuper = true)
public class CardCredentialsVerificationMethod extends DefaultVerificationMethod {

    public CardCredentialsVerificationMethod(final int duration) {
        this(duration, ELIGIBLE);
    }

    public CardCredentialsVerificationMethod(final int duration, final boolean eligible) {
        super(Names.CARD_CREDENTIALS, duration, eligible);
    }

}
