package uk.co.mruoc.idv.core.verificationcontext.service;

import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.core.channel.model.Channel;
import uk.co.mruoc.idv.core.channel.model.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.MethodSequencesRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.method.DefaultVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationSequencePolicy;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationMethodsService.AvailabilityHandlerNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VerificationMethodsServiceTest {

    private final Channel channel = new DefaultChannel("channelId");
    private final VerificationMethodPolicy methodPolicy = new VerificationMethodPolicy("methodName");
    private final VerificationPolicy policy = new VerificationPolicy("activity", new VerificationSequencePolicy(methodPolicy));

    private final MethodSequencesRequest methodsRequest = MethodSequencesRequest.builder()
            .channel(channel)
            .policy(policy)
            .build();

    private final VerificationMethodRequest methodRequest = VerificationMethodRequest.builder()
            .channel(channel)
            .methodPolicy(methodPolicy)
            .build();

    private final ThreadPoolBulkhead bulkhead = ThreadPoolBulkhead.ofDefaults("eligibilityBulkhead");

    private final AvailabilityHandler handler1 = mock(AvailabilityHandler.class);
    private final AvailabilityHandler handler2 = mock(AvailabilityHandler.class);
    private final Collection<AvailabilityHandler> handlers = Arrays.asList(handler1, handler2);
    private final VerificationMethodsRequestConverter requestConverter = mock(VerificationMethodsRequestConverter.class);

    private final VerificationMethodsService service = new DefaultVerificationMethodsService(bulkhead, handlers, requestConverter);

    @Before
    public void setUp() {
        given(requestConverter.toMethodRequest(methodsRequest, methodPolicy)).willReturn(methodRequest);
    }

    @Test
    public void shouldThrowExceptionIfNoSupportedEligiblityHandlers() {
        final Throwable thrown = catchThrowable(() -> service.loadMethodSequences(methodsRequest));

        assertThat(thrown).isInstanceOf(AvailabilityHandlerNotFoundException.class);
    }

    @Test
    public void shouldReturnVerificationMethodsFromSupportedEligibilityHandlers() {
        final VerificationMethod method = new DefaultVerificationMethod("methodName");
        given(handler1.isSupported(methodRequest)).willReturn(true);
        given(handler1.loadMethod(methodRequest)).willReturn(method);

        final Collection<VerificationMethodSequence> sequences = service.loadMethodSequences(methodsRequest);

        assertThat(sequences).hasSize(1);
        final VerificationMethodSequence sequence = new ArrayList<>(sequences).get(0);
        assertThat(sequence.getName()).isEqualTo(method.getName());
        assertThat(sequence.getMethod(method.getName())).isEqualTo(Optional.of(method));
    }

}
