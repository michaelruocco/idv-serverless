package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.ToString;

@ToString(callSuper = true)
public class CardCredentialsVerificationMethod extends DefaultVerificationMethod {

    public CardCredentialsVerificationMethod(final int duration) {
        super(Names.CARD_CREDENTIALS, duration);
    }

}
