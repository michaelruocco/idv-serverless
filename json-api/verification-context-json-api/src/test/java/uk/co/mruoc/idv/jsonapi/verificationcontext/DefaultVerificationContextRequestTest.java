package uk.co.mruoc.idv.jsonapi.verificationcontext;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.model.channel.Channel;
import uk.co.mruoc.idv.core.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class DefaultVerificationContextRequestTest {

    @Test
    public void shouldReturnChannel() {
        final Channel channel = mock(Channel.class);

        final VerificationContextRequest request = DefaultVerificationContextRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    public void shouldReturnChannelId() {
        final String channelId = "channelId";
        final Channel channel = new DefaultChannel(channelId);

        final VerificationContextRequest request = DefaultVerificationContextRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    public void shouldReturnActivity() {
        final Activity activity = mock(Activity.class);

        final VerificationContextRequest request = DefaultVerificationContextRequest.builder()
                .activity(activity)
                .build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    public void shouldReturnProvidedAlias() {
        final Alias providedAlias = mock(Alias.class);

        final VerificationContextRequest request = DefaultVerificationContextRequest.builder()
                .providedAlias(providedAlias)
                .build();

        assertThat(request.getProvidedAlias()).isEqualTo(providedAlias);
    }

}