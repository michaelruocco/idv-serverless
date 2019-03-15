package uk.co.mruoc.idv.core.verificationcontext.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.As3Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class EligibleMethodRequestTest {

    @Test
    public void shouldReturnChannel() {
        final Channel channel = mock(Channel.class);

        final EligibleMethodRequest request = EligibleMethodRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    public void shouldReturnChannelId() {
        final String channelId = "channelId";
        final Channel channel = mock(Channel.class);
        given(channel.getId()).willReturn(channelId);

        final EligibleMethodRequest request = EligibleMethodRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    public void shouldReturnInputAlias() {
        final Alias alias = mock(Alias.class);

        final EligibleMethodRequest request = EligibleMethodRequest.builder()
                .inputAlias(alias)
                .build();

        assertThat(request.getInputAlias()).isEqualTo(alias);
    }


    @Test
    public void shouldReturnIdentity() {
        final Identity identity = mock(Identity.class);

        final EligibleMethodRequest request = EligibleMethodRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    public void shouldReturnMethodPolicy() {
        final VerificationMethodPolicy methodPolicy = mock(VerificationMethodPolicy.class);

        final EligibleMethodRequest request = EligibleMethodRequest.builder()
                .methodPolicy(methodPolicy)
                .build();

        assertThat(request.getMethodPolicy()).isEqualTo(methodPolicy);
    }

    @Test
    public void shouldReturnMethodPolicyName() {
        final String methodName = "methodName";
        final VerificationMethodPolicy methodPolicy = mock(VerificationMethodPolicy.class);
        given(methodPolicy.getMethodName()).willReturn(methodName);

        final EligibleMethodRequest request = EligibleMethodRequest.builder()
                .methodPolicy(methodPolicy)
                .build();

        assertThat(request.getMethodName()).isEqualTo(methodName);
    }

    @Test
    public void shouldReturnMethodDuration() {
        final int duration = 150000;
        final VerificationMethodPolicy methodPolicy = mock(VerificationMethodPolicy.class);
        given(methodPolicy.getDuration()).willReturn(duration);

        final EligibleMethodRequest request = EligibleMethodRequest.builder()
                .methodPolicy(methodPolicy)
                .build();

        assertThat(request.getDuration()).isEqualTo(duration);
    }

    @Test
    public void shouldPrintAllValues() {
        final Alias inputAlias = new IdvIdAlias(UUID.fromString("b0d996ae-dfa0-43a4-949c-f03e9dafd539"));
        final EligibleMethodRequest request = EligibleMethodRequest.builder()
                .channel(new As3Channel())
                .inputAlias(inputAlias)
                .identity(Identity.withAliases(inputAlias))
                .methodPolicy(new VerificationMethodPolicy("method"))
                .build();

        assertThat(request.toString()).isEqualTo("EligibleMethodRequest(" +
                "channel=DefaultChannel(id=AS3), " +
                "inputAlias=DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=b0d996ae-dfa0-43a4-949c-f03e9dafd539), " +
                "identity=Identity(aliases=Aliases(aliases=[DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=b0d996ae-dfa0-43a4-949c-f03e9dafd539)])), " +
                "methodPolicy=VerificationMethodPolicy(methodName=method, duration=300000))");
    }

}
