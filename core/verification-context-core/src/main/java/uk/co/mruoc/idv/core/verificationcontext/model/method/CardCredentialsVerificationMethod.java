package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.ToString;

@ToString(callSuper = true)
public class CardCredentialsVerificationMethod extends DefaultVerificationMethod {

    public CardCredentialsVerificationMethod(final int duration) {
        this(duration, DEFAULT_STATUS, DEFAULT_MAX_ATTEMPTS);
    }

    public CardCredentialsVerificationMethod(final int duration, final VerificationStatus status, final int maxAttempts) {
        super(Names.CARD_CREDENTIALS, duration, status, maxAttempts);
    }

}
