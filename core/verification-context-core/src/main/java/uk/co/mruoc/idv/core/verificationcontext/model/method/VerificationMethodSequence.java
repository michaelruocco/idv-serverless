package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.ToString;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@ToString
public class VerificationMethodSequence {

    private final String name;
    private final Collection<VerificationMethod> methods;

    public VerificationMethodSequence(final VerificationMethod method) {
        this(method.getName(), Collections.singleton(method));
    }

    public VerificationMethodSequence(final String name, final Collection<VerificationMethod> methods) {
        this.name = name;
        this.methods = methods;
    }

    public String getName() {
        return name;
    }

    public VerificationStatus getStatus() {
        final Optional<VerificationMethod> unavailableMethod = methods.stream()
                .filter(method -> VerificationStatus.UNAVAILABLE == method.getStatus())
                .findFirst();
        if (unavailableMethod.isPresent()) {
            return VerificationStatus.UNAVAILABLE;
        }
        return VerificationStatus.AVAILABLE;
    }

    public PhysicalPinsentryVerificationMethod getPhysicalPinsentry() {
        final String methodName = VerificationMethod.Names.PHYSICAL_PINSENTRY;
        final Optional<VerificationMethod> method = getMethod(methodName);
        return method.map(verificationMethod -> (PhysicalPinsentryVerificationMethod) verificationMethod)
                .orElseThrow(() -> new VerificationMethodNotFoundInSequenceException(methodName));
    }

    public MobilePinsentryVerificationMethod getMobilePinsentry() {
        final String methodName = VerificationMethod.Names.MOBILE_PINSENTRY;
        final Optional<VerificationMethod> method = getMethod(methodName);
        return method.map(verificationMethod -> (MobilePinsentryVerificationMethod) verificationMethod)
                .orElseThrow(() -> new VerificationMethodNotFoundInSequenceException(methodName));
    }

    public PushNotificationVerificationMethod getPushNotification() {
        final String methodName = VerificationMethod.Names.PUSH_NOTIFICATION;
        final Optional<VerificationMethod> method = getMethod(methodName);
        return method.map(verificationMethod -> (PushNotificationVerificationMethod) verificationMethod)
                .orElseThrow(() -> new VerificationMethodNotFoundInSequenceException(methodName));
    }

    public CardCredentialsVerificationMethod getCardCredentials() {
        final String methodName = VerificationMethod.Names.CARD_CREDENTIALS;
        final Optional<VerificationMethod> method = getMethod(methodName);
        return method.map(verificationMethod -> (CardCredentialsVerificationMethod) verificationMethod)
                .orElseThrow(() -> new VerificationMethodNotFoundInSequenceException(methodName));
    }

    public OtpSmsVerificationMethod getOneTimePasscodeSms() {
        final String methodName = VerificationMethod.Names.ONE_TIME_PASSCODE_SMS;
        final Optional<VerificationMethod> method = getMethod(methodName);
        return method.map(verificationMethod -> (OtpSmsVerificationMethod) verificationMethod)
                .orElseThrow(() -> new VerificationMethodNotFoundInSequenceException(methodName));
    }

    public boolean isComplete(final VerificationMethodResults results) {
        final Collection<VerificationMethod> remainingMethods = new ArrayList<>(methods);
        for (final VerificationMethodResult result : results) {
            if (result.getSequenceName().equals(name)) {
                remainingMethods.removeIf(method -> method.getName().equals(result.getMethodName()));
            }
        }
        return remainingMethods.isEmpty();
    }

    public int getDuration() {
        return methods.stream().map(VerificationMethod::getDuration).mapToInt(i -> i).sum();
    }

    public boolean containsMethod(final String name) {
        return getMethod(name).isPresent();
    }

    public Optional<VerificationMethod> getMethod(final String name) {
        return methods.stream().filter(method -> method.getName().equals(name)).findFirst();
    }

    public Collection<VerificationMethod> getMethods() {
        return Collections.unmodifiableCollection(methods);
    }

    public static class VerificationMethodNotFoundInSequenceException extends RuntimeException {

        public VerificationMethodNotFoundInSequenceException(final String methodName) {
            super(methodName);
        }

    }

}
