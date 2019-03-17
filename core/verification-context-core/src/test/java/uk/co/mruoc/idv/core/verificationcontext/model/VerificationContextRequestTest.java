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

        final VerificationContextServiceRequest request = VerificationContextServiceRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    public void shouldReturnProvidedAlias() {
        final Alias alias = mock(Alias.class);

        final VerificationContextServiceRequest request = VerificationContextServiceRequest.builder()
                .providedAlias(alias)
                .build();

        assertThat(request.getProvidedAlias()).isEqualTo(alias);
    }


    @Test
    public void shouldReturnIdentity() {
        final Identity identity = mock(Identity.class);

        final VerificationContextServiceRequest request = VerificationContextServiceRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    public void shouldReturnActivity() {
        final Activity activity = mock(Activity.class);

        final VerificationContextServiceRequest request = VerificationContextServiceRequest.builder()
                .activity(activity)
                .build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    public void shouldPrintAllValues() {
        final Instant timestamp = Instant.parse("2019-03-10T12:53:57.547Z");
        final Alias inputAlias = new IdvIdAlias(UUID.fromString("b0d996ae-dfa0-43a4-949c-f03e9dafd539"));
        final VerificationContextServiceRequest request = VerificationContextServiceRequest.builder()
                .channel(new As3Channel())
                .providedAlias(inputAlias)
                .identity(Identity.withAliases(inputAlias))
                .activity(new LoginActivity(timestamp))
                .build();

        assertThat(request.toString()).isEqualTo("VerificationContextServiceRequest(" +
                "channel=DefaultChannel(id=AS3), " +
                "providedAlias=DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=b0d996ae-dfa0-43a4-949c-f03e9dafd539), " +
                "identity=Identity(aliases=Aliases(aliases=[DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=b0d996ae-dfa0-43a4-949c-f03e9dafd539)])), " +
                "activity=DefaultActivity(type=LOGIN, timestamp=2019-03-10T12:53:57.547Z, properties={}))");
    }

}
