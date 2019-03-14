package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.As3ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.BbosChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.RsaChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationPoliciesService.UnrecognisedChannelException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class VerificationPoliciesServiceTest {

    private final VerificationPoliciesService service = new VerificationPoliciesService();

    @Test
    public void shouldReturnRsaChannelVerificationPolicies() {
        final ChannelVerificationPolicies policies = service.getPoliciesForChannel(Channel.Ids.RSA);

        assertThat(policies).isInstanceOf(RsaChannelVerificationPolicies.class);
    }

    @Test
    public void shouldReturnAs3ChannelVerificationPolicies() {
        final ChannelVerificationPolicies policies = service.getPoliciesForChannel(Channel.Ids.AS3);

        assertThat(policies).isInstanceOf(As3ChannelVerificationPolicies.class);
    }

    @Test
    public void shouldReturnBbosChannelVerificationPolicies() {
        final ChannelVerificationPolicies policies = service.getPoliciesForChannel(Channel.Ids.BBOS);

        assertThat(policies).isInstanceOf(BbosChannelVerificationPolicies.class);
    }

    @Test
    public void shouldThrowExceptionForUnrecognisedChannel() {
        final String channel = "UNRECOGNISED_CHANNEL";

        final Throwable thrown = catchThrowable(() -> service.getPoliciesForChannel(channel));

        assertThat(thrown)
                .isInstanceOf(UnrecognisedChannelException.class)
                .hasMessage(channel);
    }

}
