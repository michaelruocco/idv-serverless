package uk.co.mruoc.idv.core.verificationcontext.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VerificationMethodRequestTest {

    @Test
    public void shouldReturnChannel() {
        final Channel channel = mock(Channel.class);

        final VerificationMethodRequest request = VerificationMethodRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    public void shouldReturnChannelId() {
        final String channelId = "channelId";
        final Channel channel = mock(Channel.class);
        given(channel.getId()).willReturn(channelId);

        final VerificationMethodRequest request = VerificationMethodRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    public void shouldReturnInputAlias() {
        final Alias alias = mock(Alias.class);

        final VerificationMethodRequest request = VerificationMethodRequest.builder()
                .inputAlias(alias)
                .build();

        assertThat(request.getInputAlias()).isEqualTo(alias);
    }


    @Test
    public void shouldReturnIdentity() {
        final Identity identity = mock(Identity.class);

        final VerificationMethodRequest request = VerificationMethodRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    public void shouldReturnMethodPolicy() {
        final VerificationMethodPolicy methodPolicy = mock(VerificationMethodPolicy.class);

        final VerificationMethodRequest request = VerificationMethodRequest.builder()
                .methodPolicy(methodPolicy)
                .build();

        assertThat(request.getMethodPolicy()).isEqualTo(methodPolicy);
    }

    @Test
    public void shouldReturnMethodPolicyName() {
        final String methodName = "methodName";
        final VerificationMethodPolicy methodPolicy = mock(VerificationMethodPolicy.class);
        given(methodPolicy.getMethodName()).willReturn(methodName);

        final VerificationMethodRequest request = VerificationMethodRequest.builder()
                .methodPolicy(methodPolicy)
                .build();

        assertThat(request.getMethodName()).isEqualTo(methodName);
    }

    @Test
    public void shouldReturnMethodDuration() {
        final int duration = 150000;
        final VerificationMethodPolicy methodPolicy = mock(VerificationMethodPolicy.class);
        given(methodPolicy.getDuration()).willReturn(duration);

        final VerificationMethodRequest request = VerificationMethodRequest.builder()
                .methodPolicy(methodPolicy)
                .build();

        assertThat(request.getDuration()).isEqualTo(duration);
    }

    @Test
    public void shouldPrintAllValues() {
        final Alias inputAlias = new IdvIdAlias(UUID.fromString("b0d996ae-dfa0-43a4-949c-f03e9dafd539"));
        final VerificationMethodRequest request = VerificationMethodRequest.builder()
                .channel(new DefaultChannel("CHANNEL_ID"))
                .inputAlias(inputAlias)
                .identity(Identity.withAliases(inputAlias))
                .methodPolicy(new VerificationMethodPolicy("method"))
                .build();

        assertThat(request.toString()).isEqualTo("VerificationMethodRequest(" +
                "channel=DefaultChannel(id=CHANNEL_ID), " +
                "inputAlias=DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=b0d996ae-dfa0-43a4-949c-f03e9dafd539), " +
                "identity=Identity(aliases=Aliases(aliases=[DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=b0d996ae-dfa0-43a4-949c-f03e9dafd539)])), " +
                "methodPolicy=VerificationMethodPolicy(methodName=method, duration=300000, maximumAttempts=1))");
    }

}
