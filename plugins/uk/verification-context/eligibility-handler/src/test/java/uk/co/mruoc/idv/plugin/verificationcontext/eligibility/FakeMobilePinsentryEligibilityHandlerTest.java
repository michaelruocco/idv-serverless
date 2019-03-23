package uk.co.mruoc.idv.plugin.verificationcontext.eligibility;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.MobilePinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.MobilePinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FakeMobilePinsentryEligibilityHandlerTest {

    private static final String METHOD_NAME = VerificationMethod.Names.MOBILE_PINSENTRY;

    private final EligibilityHandler handler = new FakeMobilePinsentryEligibilityHandler();

    @Test
    public void shouldSupportMobilePinsentryMethod() {
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
        final VerificationMethodPolicy methodPolicy = mock(MobilePinsentryMethodPolicy.class);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final Optional<VerificationMethod> optionalMethod = handler.loadMethodIfEligible(request);

        assertThat(optionalMethod).isNotEmpty();
    }

    @Test
    public void shouldReturnMethodWithCorrectName() {
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        final VerificationMethodPolicy methodPolicy = mock(MobilePinsentryMethodPolicy.class);
        given(request.getMethodName()).willReturn(METHOD_NAME);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final Optional<VerificationMethod> optionalMethod = handler.loadMethodIfEligible(request);

        final VerificationMethod method = optionalMethod.get();
        assertThat(method.getName()).isEqualTo(METHOD_NAME);
    }

    @Test
    public void shouldReturnMethodWithPassedDuration() {
        final int duration = 150000;
        final VerificationMethodPolicy methodPolicy = mock(MobilePinsentryMethodPolicy.class);
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        given(request.getDuration()).willReturn(duration);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final Optional<VerificationMethod> optionalMethod = handler.loadMethodIfEligible(request);

        final VerificationMethod method = optionalMethod.get();
        assertThat(method.getDuration()).isEqualTo(duration);
    }

    @Test
    public void shouldReturnMethodWithPassedPinsentryFunction() {
        final PinsentryFunction function = PinsentryFunction.IDENTIFY;
        final MobilePinsentryMethodPolicy methodPolicy = mock(MobilePinsentryMethodPolicy.class);
        given(methodPolicy.getFunction()).willReturn(function);
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final Optional<VerificationMethod> optionalMethod = handler.loadMethodIfEligible(request);

        final MobilePinsentryVerificationMethod method = (MobilePinsentryVerificationMethod) optionalMethod.get();
        assertThat(method.getFunction()).isEqualTo(function);
    }

}
