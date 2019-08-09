package uk.co.mruoc.idv.plugin.uk.verificationcontext.availability;

import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

public class FakeCardCredentialsAvailabilityHandler extends DelayedAvailabilityHandler {

    private static final String DELAY_ENVIRONMENT_VARIABLE_NAME = "CARD_CREDENTIALS_ELIGIBILITY_DELAY";
    private static final String METHOD_NAME = VerificationMethod.Names.CARD_CREDENTIALS;
    private static final VerificationMethodRequestConverter CONVERTER = new CardCredentialsVerificationMethodRequestConverter();

    public FakeCardCredentialsAvailabilityHandler() {
        super(CONVERTER, METHOD_NAME, DELAY_ENVIRONMENT_VARIABLE_NAME);
    }

}
