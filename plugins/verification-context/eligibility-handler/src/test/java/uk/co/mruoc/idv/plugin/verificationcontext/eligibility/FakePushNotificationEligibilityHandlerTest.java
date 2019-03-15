package uk.co.mruoc.idv.plugin.verificationcontext.eligibility;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibilityHandler;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FakePushNotificationEligibilityHandlerTest {

    private static final String METHOD_NAME = VerificationMethod.Names.PUSH_NOTIFICATION;

    private final EligibilityHandler handler = new FakePushNotificationEligibilityHandler();

    @Test
    public void shouldSupportPushNotificationMethod() {
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        given(request.getMethodName()).willReturn(METHOD_NAME);

        final boolean supported = handler.isSupported(request);

        assertThat(supported).isTrue();
    }

    @Test
    public void shouldSupportAnyOtherMethods() {
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        given(request.getMethodName()).willReturn("method");

        final boolean supported = handler.isSupported(request);

        assertThat(supported).isFalse();
    }

    @Test
    public void shouldReturnPushNotificationWithPassedDuration() {
        final int duration = 150000;
        final EligibleMethodRequest request = mock(EligibleMethodRequest.class);
        given(request.getDuration()).willReturn(duration);

        final Optional<VerificationMethod> optionalMethod = handler.loadMethodIfEligible(request);

        assertThat(optionalMethod).isNotEmpty();
        final VerificationMethod method = optionalMethod.get();
        assertThat(method.getName()).isEqualTo(METHOD_NAME);
        assertThat(method.getDuration()).isEqualTo(duration);
    }

}
