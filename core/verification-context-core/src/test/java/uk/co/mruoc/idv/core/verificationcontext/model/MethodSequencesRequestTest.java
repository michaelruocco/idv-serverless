package uk.co.mruoc.idv.core.verificationcontext.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.channel.model.Channel;
import uk.co.mruoc.idv.core.channel.model.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationPolicy;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MethodSequencesRequestTest {

    @Test
    public void shouldReturnChannel() {
        final Channel channel = mock(Channel.class);

        final MethodSequencesRequest request = MethodSequencesRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    public void shouldReturnInputAlias() {
        final Alias alias = mock(Alias.class);

        final MethodSequencesRequest request = MethodSequencesRequest.builder()
                .inputAlias(alias)
                .build();

        assertThat(request.getInputAlias()).isEqualTo(alias);
    }


    @Test
    public void shouldReturnIdentity() {
        final Identity identity = mock(Identity.class);

        final MethodSequencesRequest request = MethodSequencesRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    public void shouldReturnPolicy() {
        final VerificationPolicy policy = mock(VerificationPolicy.class);

        final MethodSequencesRequest request = MethodSequencesRequest.builder()
                .policy(policy)
                .build();

        assertThat(request.getPolicy()).isEqualTo(policy);
    }

    @Test
    public void shouldPrintAllValues() {
        final Alias inputAlias = new IdvIdAlias(UUID.fromString("b0d996ae-dfa0-43a4-949c-f03e9dafd539"));
        final MethodSequencesRequest request = MethodSequencesRequest.builder()
                .channel(new DefaultChannel("CHANNEL"))
                .inputAlias(inputAlias)
                .identity(Identity.withAliases(inputAlias))
                .policy(new VerificationPolicy("type", Collections.emptyList()))
                .build();

        assertThat(request.toString()).isEqualTo("MethodSequencesRequest(" +
                "channel=DefaultChannel(id=CHANNEL), " +
                "inputAlias=DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=b0d996ae-dfa0-43a4-949c-f03e9dafd539), " +
                "identity=Identity(aliases=Aliases(aliases=[DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=b0d996ae-dfa0-43a4-949c-f03e9dafd539)])), " +
                "policy=VerificationPolicy(activityTypes=[type], entries=[]))");
    }

}
