package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationPoliciesService.VerificationPolicyNotConfiguredForChannelException;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VerificationPoliciesServiceTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    private final ChannelVerificationPolicies policies = mock(ChannelVerificationPolicies.class);

    private VerificationPoliciesService service;

    @Before
    public void setUp() {
        given(policies.getChannelId()).willReturn(CHANNEL_ID);

        service = new VerificationPoliciesService(Collections.singletonList(policies));
    }

    @Test
    public void shouldReturnRsaChannelVerificationPolicies() {
        final ChannelVerificationPolicies policies = service.getPoliciesForChannel(CHANNEL_ID);

        assertThat(policies).isEqualTo(policies);
    }

    @Test
    public void shouldThrowExceptionForUnrecognisedChannel() {
        final String channel = "UNRECOGNISED_CHANNEL";

        final Throwable thrown = catchThrowable(() -> service.getPoliciesForChannel(channel));

        assertThat(thrown)
                .isInstanceOf(VerificationPolicyNotConfiguredForChannelException.class)
                .hasMessage(channel);
    }

}
