package uk.co.mruoc.idv.core.verificationcontext.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.As3Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class VerificationContextRequestTest {

    @Test
    public void shouldReturnChannel() {
        final Channel channel = mock(Channel.class);

        final VerificationContextRequest request = VerificationContextRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    public void shouldReturnInputAlias() {
        final Alias alias = mock(Alias.class);

        final VerificationContextRequest request = VerificationContextRequest.builder()
                .inputAlias(alias)
                .build();

        assertThat(request.getInputAlias()).isEqualTo(alias);
    }


    @Test
    public void shouldReturnIdentity() {
        final Identity identity = mock(Identity.class);

        final VerificationContextRequest request = VerificationContextRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    public void shouldReturnActivity() {
        final Activity activity = mock(Activity.class);

        final VerificationContextRequest request = VerificationContextRequest.builder()
                .activity(activity)
                .build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    public void shouldPrintAllValues() {
        final Instant timestamp = Instant.parse("2019-03-10T12:53:57.547Z");
        final Alias inputAlias = new IdvIdAlias(UUID.fromString("b0d996ae-dfa0-43a4-949c-f03e9dafd539"));
        final VerificationContextRequest request = VerificationContextRequest.builder()
                .channel(new As3Channel())
                .inputAlias(inputAlias)
                .identity(Identity.withAliases(inputAlias))
                .activity(new LoginActivity(timestamp))
                .build();

        assertThat(request.toString()).isEqualTo("VerificationContextRequest(" +
                "channel=DefaultChannel(id=AS3), " +
                "inputAlias=DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=b0d996ae-dfa0-43a4-949c-f03e9dafd539), " +
                "identity=Identity(aliases=Aliases(aliases=[DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=b0d996ae-dfa0-43a4-949c-f03e9dafd539)])), " +
                "activity=DefaultActivity(type=LOGIN, timestamp=2019-03-10T12:53:57.547Z, properties={}))");
    }

}
