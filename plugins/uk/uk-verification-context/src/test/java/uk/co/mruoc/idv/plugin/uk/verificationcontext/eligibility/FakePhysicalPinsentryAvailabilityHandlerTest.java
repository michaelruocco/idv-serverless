package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import org.junit.Test;
import uk.co.mruoc.idv.core.model.CardNumber;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PhysicalPinsentryVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PinsentryFunction;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.PhysicalPinsentryMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.AvailabilityHandler;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FakePhysicalPinsentryAvailabilityHandlerTest {

    private static final String METHOD_NAME = VerificationMethod.Names.PHYSICAL_PINSENTRY;

    private final AvailabilityHandler handler = new FakePhysicalPinsentryAvailabilityHandler();

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
    public void shouldReturnMethod() throws Exception {
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        final VerificationMethodPolicy methodPolicy = mock(PhysicalPinsentryMethodPolicy.class);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final CompletableFuture<VerificationMethod> method = handler.loadMethod(request);

        assertThat(method.get()).isNotNull();
    }

    @Test
    public void shouldReturnMethodWithCorrectName() throws Exception {
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        final VerificationMethodPolicy methodPolicy = mock(PhysicalPinsentryMethodPolicy.class);
        given(request.getMethodName()).willReturn(METHOD_NAME);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final CompletableFuture<VerificationMethod> method = handler.loadMethod(request);

        assertThat(method.get().getName()).isEqualTo(METHOD_NAME);
    }

    @Test
    public void shouldReturnMethodWithPassedDuration() throws Exception {
        final int duration = 150000;
        final VerificationMethodPolicy methodPolicy = mock(PhysicalPinsentryMethodPolicy.class);
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        given(request.getDuration()).willReturn(duration);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final CompletableFuture<VerificationMethod> method = handler.loadMethod(request);

        assertThat(method.get().getDuration()).isEqualTo(duration);
    }

    @Test
    public void shouldReturnMethodWithPassedPinsentryFunction() throws Exception {
        final PinsentryFunction function = PinsentryFunction.IDENTIFY;
        final PhysicalPinsentryMethodPolicy methodPolicy = mock(PhysicalPinsentryMethodPolicy.class);
        given(methodPolicy.getFunction()).willReturn(function);
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final CompletableFuture<VerificationMethod> method = handler.loadMethod(request);

        final PhysicalPinsentryVerificationMethod physicalPinsentry = (PhysicalPinsentryVerificationMethod) method.get();
        assertThat(physicalPinsentry.getFunction()).isEqualTo(function);
    }

    @Test
    public void shouldReturnMethodWithCardNumbers() throws Exception {
        final PhysicalPinsentryMethodPolicy methodPolicy = mock(PhysicalPinsentryMethodPolicy.class);
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        given(request.getMethodPolicy()).willReturn(methodPolicy);

        final CompletableFuture<VerificationMethod> method = handler.loadMethod(request);

        final PhysicalPinsentryVerificationMethod physicalPinsentry = (PhysicalPinsentryVerificationMethod) method.get();
        final CardNumber expectedCardNumber = CardNumber.builder()
                .tokenized("3213485412348005")
                .masked("************8005")
                .build();
        assertThat(physicalPinsentry.getCardNumbers())
                .usingElementComparator(new CardNumberComparator())
                .contains(expectedCardNumber);
    }

}
