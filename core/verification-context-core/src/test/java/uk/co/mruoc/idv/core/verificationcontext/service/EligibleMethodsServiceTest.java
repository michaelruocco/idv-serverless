package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodRequest;
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

    private final EligibleMethodsRequest methodsRequest = EligibleMethodsRequest.builder()
            .channel(channel)
            .policy(policy)
            .build();

    private final EligibleMethodRequest methodRequest = EligibleMethodRequest.builder()
            .channel(channel)
            .methodPolicy(methodPolicy)
            .build();

    private final EligibilityHandler handler1 = mock(EligibilityHandler.class);
    private final EligibilityHandler handler2 = mock(EligibilityHandler.class);
    private final Collection<EligibilityHandler> handlers = Arrays.asList(handler1, handler2);
    private final EligibleMethodsRequestConverter requestConverter = mock(EligibleMethodsRequestConverter.class);

    private final EligibleMethodsService service = new DefaultEligibleMethodsService(handlers, requestConverter);

    @Before
    public void setUp() {
        given(requestConverter.toMethodRequest(methodsRequest, methodPolicy)).willReturn(methodRequest);
    }

    @Test
    public void shouldThrowExceptionIfNoSupportedEligiblityHandlers() {
        final Throwable thrown = catchThrowable(() -> service.loadEligibleMethods(methodsRequest));

        assertThat(thrown).isInstanceOf(EligibilityHandlerNotFoundException.class)
                .hasMessage("eligibility handler for channel channelId and method methodName not found");
    }

    @Test
    public void shouldReturnVerificationMethodsFromSupportedEligibilityHandlers() {
        final VerificationMethod method = new DefaultVerificationMethod("methodName");
        given(handler1.isSupported(methodRequest)).willReturn(true);
        given(handler1.loadMethodIfEligible(methodRequest)).willReturn(Optional.of(method));

        final Collection<VerificationMethodSequence> sequences = service.loadEligibleMethods(methodsRequest);

        assertThat(sequences).hasSize(1);
        final VerificationMethodSequence sequence = new ArrayList<>(sequences).get(0);
        assertThat(sequence.getName()).isEqualTo(method.getName());
        assertThat(sequence.getMethod(method.getName())).isEqualTo(Optional.of(method));
    }

}
