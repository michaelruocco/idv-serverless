package uk.co.mruoc.idv.plugin.uk.verificationcontext.availability;

import org.junit.Test;
import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.OtpSmsMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.AvailabilityHandler;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FakeOtpSmsAvailabilityHandlerTest {

    private static final String METHOD_NAME = VerificationMethod.Names.ONE_TIME_PASSCODE_SMS;

    private final AvailabilityHandler handler = new FakeOtpSmsAvailabilityHandler();

    @Test
    public void shouldSupportPhysicalPinsentryMethod() {
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        given(request.getMethodName()).willReturn(METHOD_NAME);

        final boolean supported = handler.isSupported(request);

        assertThat(supported).isTrue();
    }

    @Test
    public void shouldNotSupportAnyOtherMethods() {
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        given(request.getMethodName()).willReturn("method");

        final boolean supported = handler.isSupported(request);

        assertThat(supported).isFalse();
    }

    @Test
    public void shouldReturnMethod() {
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        final VerificationMethodPolicy methodPolicy = mock(OtpSmsMethodPolicy.class);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final VerificationMethod method = handler.loadMethod(request);

        assertThat(method).isNotNull();
    }

    @Test
    public void shouldReturnMethodWithCorrectName() {
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        final VerificationMethodPolicy methodPolicy = mock(OtpSmsMethodPolicy.class);
        given(request.getMethodName()).willReturn(METHOD_NAME);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final VerificationMethod method = handler.loadMethod(request);

        assertThat(method.getName()).isEqualTo(METHOD_NAME);
    }

    @Test
    public void shouldReturnMethodWithPassedDuration() {
        final int duration = 150000;
        final VerificationMethodPolicy methodPolicy = mock(OtpSmsMethodPolicy.class);
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        given(request.getDuration()).willReturn(duration);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final VerificationMethod method = handler.loadMethod(request);

        assertThat(method.getDuration()).isEqualTo(duration);
    }

    @Test
    public void shouldReturnMethodWithPassedPasscode() {
        final Passcode passcode = mock(Passcode.class);
        final OtpSmsMethodPolicy methodPolicy = mock(OtpSmsMethodPolicy.class);
        given(methodPolicy.getPasscode()).willReturn(passcode);
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final VerificationMethod method = handler.loadMethod(request);

        final OtpSmsVerificationMethod otpSms = (OtpSmsVerificationMethod) method;
        assertThat(otpSms.getPasscode()).isEqualTo(passcode);
    }

    @Test
    public void shouldReturnMethodWithMobileNumbers() {
        final OtpSmsMethodPolicy methodPolicy = mock(OtpSmsMethodPolicy.class);
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final VerificationMethod method = handler.loadMethod(request);

        final OtpSmsVerificationMethod otpSms = (OtpSmsVerificationMethod) method;
        final MobileNumber expectedMobileNumber = MobileNumber.builder()
                .id(UUID.fromString("48be7f28-37c2-42b6-956c-a32241310ee6"))
                .masked("********789")
                .build();
        assertThat(otpSms.getMobileNumbers())
                .usingElementComparator(new MobileNumberComparator())
                .contains(expectedMobileNumber);
    }

}
