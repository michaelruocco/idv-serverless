package uk.co.mruoc.idv.plugin.uk.verificationcontext.availability;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.CardCredentialsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationStatus;
import uk.co.mruoc.idv.core.verificationcontext.service.AvailabilityHandler;

import java.util.concurrent.TimeUnit;

@Slf4j
public class FakeCardCredentialsAvailabilityHandler implements AvailabilityHandler {

    private static final String METHOD_NAME = VerificationMethod.Names.CARD_CREDENTIALS;

    @Override
    public VerificationMethod loadMethod(final VerificationMethodRequest request) {
        try {
            TimeUnit.MILLISECONDS.sleep(150);
            return new CardCredentialsVerificationMethod(request.getDuration());
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("interrupted exception", e);
            return new CardCredentialsVerificationMethod(request.getDuration(), VerificationStatus.UNAVAILABLE);
        }
    }

    @Override
    public boolean isSupported(final VerificationMethodRequest request) {
        return METHOD_NAME.equals(request.getMethodName());
    }

}
