package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

@Getter
public class CardCredentialsMethodPolicy extends VerificationMethodPolicy {

    public CardCredentialsMethodPolicy() {
        this(DEFAULT_DURATION);
    }

    public CardCredentialsMethodPolicy(final int duration) {
        super(VerificationMethod.Names.CARD_CREDENTIALS, duration);
    }

}
