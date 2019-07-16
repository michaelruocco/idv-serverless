package uk.co.mruoc.idv.plugin.uk.verificationcontext.eligibility;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.service.AvailabilityHandler;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FakeCardCredentialsAvailabilityHandlerTest {

    private static final String METHOD_NAME = VerificationMethod.Names.CARD_CREDENTIALS;

    private final AvailabilityHandler handler = new FakeCardCredentialsAvailabilityHandler();

    @Test
    public void shouldSupportCardCredentialsMethod() {
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
        given(request.getMethodName()).willReturn(METHOD_NAME);

        final CompletableFuture<VerificationMethod> method = handler.loadMethod(request);

        assertThat(method.get()).isNotNull();
    }

    @Test
    public void shouldReturnMethodWithCorrectName() throws Exception {
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        given(request.getMethodName()).willReturn(METHOD_NAME);

        final CompletableFuture<VerificationMethod> method = handler.loadMethod(request);

        assertThat(method.get().getName()).isEqualTo(METHOD_NAME);
    }

    @Test
    public void shouldReturnMethodWithPassedDuration() throws Exception {
        final int duration = 150000;
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        given(request.getDuration()).willReturn(duration);

        final CompletableFuture<VerificationMethod> method = handler.loadMethod(request);

        assertThat(method.get().getDuration()).isEqualTo(duration);
    }

}
