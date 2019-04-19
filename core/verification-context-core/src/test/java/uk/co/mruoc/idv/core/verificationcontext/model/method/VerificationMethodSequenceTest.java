package uk.co.mruoc.idv.core.verificationcontext.model.method;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence.VerificationMethodNotFoundInSequenceException;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.FailureStrategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class VerificationMethodSequenceTest {

    @Test
    public void shouldReturnSpecifiedName() {
        final String specifiedName = "specifiedName";

        final VerificationMethodSequence sequence = new VerificationMethodSequence(specifiedName, Collections.emptyList());

        assertThat(sequence.getName()).isEqualTo(specifiedName);
    }

    @Test
    public void shouldReturnImmediateFailureStrategyByDefault() {
        final VerificationMethodSequence sequence = new VerificationMethodSequence("name", Collections.emptyList());

        assertThat(sequence.getFailureStrategy()).isEqualTo(FailureStrategy.IMMEDIATE);
    }

    @Test
    public void shouldReturnSpecifiedFailureStrategy() {
        final FailureStrategy failureStrategy = FailureStrategy.ON_COMPLETION;

        final VerificationMethodSequence sequence = new VerificationMethodSequence("name", Collections.emptyList(), failureStrategy);

        assertThat(sequence.getFailureStrategy()).isEqualTo(failureStrategy);
    }

    @Test
    public void shouldReturnMethodNameIfOneMethodPassed() {
        final String name = "methodName";
        final VerificationMethod method = new DefaultVerificationMethod(name);

        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        assertThat(sequence.getName()).isEqualTo(name);
    }

    @Test
    public void shouldReturnWhetherSequenceContainsMethod() {
        final String name = "methodName";
        final VerificationMethod method = new DefaultVerificationMethod(name);

        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        assertThat(sequence.containsMethod(name)).isTrue();
        assertThat(sequence.containsMethod("anotherMethod")).isFalse();
    }

    @Test
    public void getDurationShouldReturnSumOfAllMethodDurations() {
        final VerificationMethod method1 = new DefaultVerificationMethod("method1", 15000);
        final VerificationMethod method2 = new DefaultVerificationMethod("method2", 15000);

        final VerificationMethodSequence sequence = new VerificationMethodSequence("name", Arrays.asList(method1, method2));

        final int sumOfDurations = method1.getDuration() + method2.getDuration();
        assertThat(sequence.getDuration()).isEqualTo(sumOfDurations);
    }

    @Test
    public void shouldThrowExceptionIfPhysicalPinsentryMethodNotInSequence() {
        final VerificationMethod method = new DefaultVerificationMethod("method");
        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        final Throwable thrown = catchThrowable(sequence::getPhysicalPinsentry);

        assertThat(thrown).isInstanceOf(VerificationMethodNotFoundInSequenceException.class)
                .hasMessage(VerificationMethod.Names.PHYSICAL_PINSENTRY);
    }

    @Test
    public void shouldThrowExceptionIfMobilePinsentryMethodNotInSequence() {
        final VerificationMethod method = new DefaultVerificationMethod("method");
        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        final Throwable thrown = catchThrowable(sequence::getMobilePinsentry);

        assertThat(thrown).isInstanceOf(VerificationMethodNotFoundInSequenceException.class)
                .hasMessage(VerificationMethod.Names.MOBILE_PINSENTRY);
    }

    @Test
    public void shouldThrowExceptionIfPushNotificationMethodNotInSequence() {
        final VerificationMethod method = new DefaultVerificationMethod("method");
        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        final Throwable thrown = catchThrowable(sequence::getPushNotification);

        assertThat(thrown).isInstanceOf(VerificationMethodNotFoundInSequenceException.class)
                .hasMessage(VerificationMethod.Names.PUSH_NOTIFICATION);
    }

    @Test
    public void shouldThrowExceptionIfCardCredentialsMethodNotInSequence() {
        final VerificationMethod method = new DefaultVerificationMethod("method");
        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        final Throwable thrown = catchThrowable(sequence::getCardCredentials);

        assertThat(thrown).isInstanceOf(VerificationMethodNotFoundInSequenceException.class)
                .hasMessage(VerificationMethod.Names.CARD_CREDENTIALS);
    }

    @Test
    public void shouldThrowExceptionIfOneTimePasscodeSmsMethodNotInSequence() {
        final VerificationMethod method = new DefaultVerificationMethod("method");
        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        final Throwable thrown = catchThrowable(sequence::getOneTimePasscodeSms);

        assertThat(thrown).isInstanceOf(VerificationMethodNotFoundInSequenceException.class)
                .hasMessage(VerificationMethod.Names.ONE_TIME_PASSCODE_SMS);
    }

    @Test
    public void shouldReturnPhysicalPinsentry() {
        final VerificationMethod method = new FakePhysicalPinsentryVerificationMethod();

        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        assertThat(sequence.getPhysicalPinsentry()).isEqualTo(method);
    }

    @Test
    public void shouldReturnMobilePinsentry() {
        final VerificationMethod method = new FakeMobilePinsentryVerificationMethod();

        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        assertThat(sequence.getMobilePinsentry()).isEqualTo(method);
    }

    @Test
    public void shouldReturnPushNotification() {
        final VerificationMethod method = new FakePushNotificationVerificationMethod();

        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        assertThat(sequence.getPushNotification()).isEqualTo(method);
    }

    @Test
    public void shouldReturnCardCredentials() {
        final VerificationMethod method = new FakeCardCredentialsVerificationMethod();

        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        assertThat(sequence.getCardCredentials()).isEqualTo(method);
    }

    @Test
    public void shouldReturnMethodByName() {
        final VerificationMethod method = new FakeCardCredentialsVerificationMethod();

        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        assertThat(sequence.getMethod(method.getName())).isEqualTo(Optional.of(method));
        assertThat(sequence.getMethod("otherName")).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldReturnOneTimePasscodeSms() {
        final VerificationMethod method = new FakeOtpSmsVerificationMethod();

        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        assertThat(sequence.getOneTimePasscodeSms()).isEqualTo(method);
    }

    @Test
    public void shouldPrintAllValues() {
        final VerificationMethod method = new FakePushNotificationVerificationMethod();

        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);

        assertThat(sequence.toString()).isEqualTo("VerificationMethodSequence(name=PUSH_NOTIFICATION, " +
                "failureStrategy=IMMEDIATE, methods=[PushNotificationVerificationMethod(super=DefaultVerificationMethod(" +
                "name=PUSH_NOTIFICATION, duration=0, status=AVAILABLE, maxAttempts=1, properties={}))])");
    }

    private static class FakePhysicalPinsentryVerificationMethod extends PhysicalPinsentryVerificationMethod {

        private FakePhysicalPinsentryVerificationMethod() {
            super(0, PinsentryFunction.IDENTIFY, Collections.emptyList());
        }

    }

    private static class FakeMobilePinsentryVerificationMethod extends MobilePinsentryVerificationMethod {

        private FakeMobilePinsentryVerificationMethod() {
            super(0, PinsentryFunction.IDENTIFY);
        }

    }

    private static class FakePushNotificationVerificationMethod extends PushNotificationVerificationMethod {

        private FakePushNotificationVerificationMethod() {
            super(0);
        }

    }

    private static class FakeCardCredentialsVerificationMethod extends CardCredentialsVerificationMethod {

        private FakeCardCredentialsVerificationMethod() {
            super(0);
        }

    }

    private static class FakeOtpSmsVerificationMethod extends OtpSmsVerificationMethod {

        private static final Passcode PASSCODE = Passcode.builder()
                .duration(0)
                .length(0)
                .maxAttempts(0)
                .build();

        private FakeOtpSmsVerificationMethod() {
            super(0, PASSCODE, Collections.emptyList());
        }

    }

}
