package uk.co.mruoc.idv.plugin.uk.verificationcontext.availability;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.service.AvailabilityHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FakePushNotificationAvailabilityHandlerTest {

    private static final String METHOD_NAME = VerificationMethod.Names.PUSH_NOTIFICATION;

    private final AvailabilityHandler handler = new FakePushNotificationAvailabilityHandler();

    @Test
    public void shouldSupportPushNotificationMethod() {
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
        given(request.getMethodName()).willReturn(METHOD_NAME);

        final VerificationMethod method = handler.loadMethod(request);

        assertThat(method).isNotNull();
    }

    @Test
    public void shouldReturnMethodWithCorrectName() {
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        given(request.getMethodName()).willReturn(METHOD_NAME);

        final VerificationMethod method = handler.loadMethod(request);

        assertThat(method.getName()).isEqualTo(METHOD_NAME);
    }

    @Test
    public void shouldReturnPushNotificationWithPassedDuration() {
        final int duration = 150000;
        final VerificationMethodRequest request = mock(VerificationMethodRequest.class);
        given(request.getDuration()).willReturn(duration);

        final VerificationMethod method = handler.loadMethod(request);

        assertThat(method.getDuration()).isEqualTo(duration);
    }

}
