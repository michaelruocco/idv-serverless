package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.DefaultVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicyEntry;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.EligibleMethodsService.EligibilityHandlerNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class EligibleMethodsServiceTest {

    private final Channel channel = new DefaultChannel("channelId");
    private final VerificationMethodPolicy methodPolicy = new VerificationMethodPolicy("methodName");
    private final VerificationPolicy policy = new VerificationPolicy("activity", new VerificationMethodPolicyEntry(methodPolicy));

    private final EligibilityHandler handler1 = mock(EligibilityHandler.class);
    private final EligibilityHandler handler2 = mock(EligibilityHandler.class);

    private final EligibleMethodsService service = new EligibleMethodsService(Arrays.asList(handler1, handler2));

    @Test
    public void shouldThrowExceptionIfNoSupportedEligiblityHandlers() {
        final EligibleMethodsRequest request = EligibleMethodsRequest.builder()
                .channel(channel)
                .policy(policy)
                .build();

        final Throwable thrown = catchThrowable(() -> service.loadEligibleMethods(request));

        assertThat(thrown).isInstanceOf(EligibilityHandlerNotFoundException.class)
                .hasMessage("eligibility handler for channel channelId and method methodName not found");
    }

    @Test
    public void shouldReturnVerificationMethodsFromSupportedEligibilityHandlers() {
        final EligibleMethodsRequest request = EligibleMethodsRequest.builder()
                .channel(channel)
                .policy(policy)
                .build();
        final VerificationMethod method = new DefaultVerificationMethod("methodName");
        given(handler1.isSupported(channel.getId(), methodPolicy.getMethodName())).willReturn(true);
        given(handler1.loadMethodIfEligible(request, methodPolicy)).willReturn(Optional.of(method));

        final Collection<VerificationMethodSequence> sequences = service.loadEligibleMethods(request);

        assertThat(sequences).hasSize(1);
        final VerificationMethodSequence sequence = new ArrayList<>(sequences).get(0);
        assertThat(sequence.getName()).isEqualTo(method.getName());
        assertThat(sequence.getMethod(method.getName())).isEqualTo(Optional.of(method));
    }

}
