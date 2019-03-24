package uk.co.mruoc.idv.plugin.uk.verificationcontext.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.ChannelVerificationPolicies;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationPoliciesService;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationPoliciesService.VerificationPolicyNotConfiguredForChannelException;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.channel.UkChannel;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.as3.As3ChannelVerificationPolicies;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.bbos.BbosChannelVerificationPolicies;
import uk.co.mruoc.idv.plugin.uk.verificationcontext.policy.rsa.RsaChannelVerificationPolicies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class UkVerificationPoliciesServiceTest {

    private final VerificationPoliciesService service = new UkVerificationPoliciesService();

    @Test
    public void shouldReturnRsaChannelVerificationPolicies() {
        final ChannelVerificationPolicies policies = service.getPoliciesForChannel(UkChannel.Ids.RSA);

        assertThat(policies).isInstanceOf(RsaChannelVerificationPolicies.class);
    }

    @Test
    public void shouldReturnAs3ChannelVerificationPolicies() {
        final ChannelVerificationPolicies policies = service.getPoliciesForChannel(UkChannel.Ids.AS3);

        assertThat(policies).isInstanceOf(As3ChannelVerificationPolicies.class);
    }

    @Test
    public void shouldReturnBbosChannelVerificationPolicies() {
        final ChannelVerificationPolicies policies = service.getPoliciesForChannel(UkChannel.Ids.BBOS);

        assertThat(policies).isInstanceOf(BbosChannelVerificationPolicies.class);
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
