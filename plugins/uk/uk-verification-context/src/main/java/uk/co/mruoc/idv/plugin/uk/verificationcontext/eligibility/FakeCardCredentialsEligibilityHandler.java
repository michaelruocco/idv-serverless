package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

public class FakeCardCredentialsEligibilityHandler extends DelayedEligibilityHandler {

    private static final String DELAY_ENVIRONMENT_VARIABLE_NAME = "CARD_CREDENTIALS_ELIGIBILITY_DELAY";
    private static final String METHOD_NAME = VerificationMethod.Names.CARD_CREDENTIALS;
    private static final VerificationMethodRequestConverter CONVERTER = new CardCredentialsVerificationMethodRequestConverter();

    public FakeCardCredentialsEligibilityHandler() {
        super(CONVERTER, METHOD_NAME, DELAY_ENVIRONMENT_VARIABLE_NAME);
    }

}
