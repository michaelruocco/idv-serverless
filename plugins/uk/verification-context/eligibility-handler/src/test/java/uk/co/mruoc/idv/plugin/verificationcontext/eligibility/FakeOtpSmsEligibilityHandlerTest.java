package uk.co.mruoc.idv.plugin.verificationcontext.eligibility;

import org.junit.Test;
import uk.co.mruoc.idv.core.model.MobileNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.OtpSmsVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.Passcode;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.OtpSmsMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FakeOtpSmsEligibilityHandlerTest {

    private static final String METHOD_NAME = VerificationMethod.Names.ONE_TIME_PASSCODE_SMS;

    private final EligibilityHandler handler = new FakeOtpSmsEligibilityHandler();

    @Test
    public void shouldSupportPhysicalPinsentryMethod() {
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        given(request.getMethodName()).willReturn(METHOD_NAME);

        final boolean supported = handler.isSupported(request);

        assertThat(supported).isTrue();
    }

    @Test
    public void shouldNotSupportAnyOtherMethods() {
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        given(request.getMethodName()).willReturn("method");

        final boolean supported = handler.isSupported(request);

        assertThat(supported).isFalse();
    }

    @Test
    public void shouldReturnMethod() {
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        final VerificationMethodPolicy methodPolicy = mock(OtpSmsMethodPolicy.class);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final Optional<VerificationMethod> optionalMethod = handler.loadMethodIfEligible(request);

        assertThat(optionalMethod).isNotEmpty();
    }

    @Test
    public void shouldReturnMethodWithCorrectName() {
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        final VerificationMethodPolicy methodPolicy = mock(OtpSmsMethodPolicy.class);
        given(request.getMethodName()).willReturn(METHOD_NAME);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final Optional<VerificationMethod> optionalMethod = handler.loadMethodIfEligible(request);

        final VerificationMethod method = optionalMethod.get();
        assertThat(method.getName()).isEqualTo(METHOD_NAME);
    }

    @Test
    public void shouldReturnMethodWithPassedDuration() {
        final int duration = 150000;
        final VerificationMethodPolicy methodPolicy = mock(OtpSmsMethodPolicy.class);
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        given(request.getDuration()).willReturn(duration);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final Optional<VerificationMethod> optionalMethod = handler.loadMethodIfEligible(request);

        final VerificationMethod method = optionalMethod.get();
        assertThat(method.getDuration()).isEqualTo(duration);
    }

    @Test
    public void shouldReturnMethodWithPassedPasscode() {
        final Passcode passcode = mock(Passcode.class);
        final OtpSmsMethodPolicy methodPolicy = mock(OtpSmsMethodPolicy.class);
        given(methodPolicy.getPasscode()).willReturn(passcode);
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final Optional<VerificationMethod> optionalMethod = handler.loadMethodIfEligible(request);

        final OtpSmsVerificationMethod method = (OtpSmsVerificationMethod) optionalMethod.get();
        assertThat(method.getPasscode()).isEqualTo(passcode);
    }

    @Test
    public void shouldReturnMethodWithMobileNumbers() {
        final OtpSmsMethodPolicy methodPolicy = mock(OtpSmsMethodPolicy.class);
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final Optional<VerificationMethod> optionalMethod = handler.loadMethodIfEligible(request);

        final OtpSmsVerificationMethod method = (OtpSmsVerificationMethod) optionalMethod.get();
        final MobileNumber expectedMobileNumber = MobileNumber.builder()
                .id(UUID.fromString("48be7f28-37c2-42b6-956c-a32241310ee6"))
                .masked("********789")
                .build();
        assertThat(method.getMobileNumbers())
                .usingElementComparator(new MobileNumberComparator())
                .contains(expectedMobileNumber);
    }

}
