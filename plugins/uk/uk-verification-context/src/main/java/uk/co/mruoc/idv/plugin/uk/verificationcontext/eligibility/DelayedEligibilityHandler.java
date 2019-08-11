package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DelayedEligibilityHandler implements EligibilityHandler {

    private final VerificationMethodRequestConverter converter;
    private final String methodName;
    private final int delay;

    public DelayedEligibilityHandler(final VerificationMethodRequestConverter converter,
                                     final String methodName,
                                     final String delayEnvironmentVariableName) {
        this(converter, methodName, getDelay(delayEnvironmentVariableName));
    }

    public DelayedEligibilityHandler(final VerificationMethodRequestConverter converter,
                                     final String methodName,
                                     final int delay) {
        this.converter = converter;
        this.methodName = methodName;
        this.delay = delay;
    }

    @Override
    public VerificationMethod loadMethod(final VerificationMethodRequest request) {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
            return converter.toAvailableVerificationMethod(request);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("interrupted exception", e);
            return converter.toUnavailableVerificationMethod(request);
        }
    }

    @Override
    public boolean isSupported(final VerificationMethodRequest request) {
        return methodName.equals(request.getMethodName());
    }

    private static int getDelay(final String delayEnvironmentVariableName) {
        final Map<String, String> env = System.getenv();
        final String value = env.getOrDefault(delayEnvironmentVariableName, "0");
        log.info("loaded {} delay value {}", delayEnvironmentVariableName, value);
        return Integer.parseInt(value);
    }

}
