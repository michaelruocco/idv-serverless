package uk.co.mruoc.idv.awslambda.verificationcontext;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextServiceRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class VerificationContextRequestConverterTest {

    private final Identity identity = mock(Identity.class);

    private final VerificationContextRequestConverter converter = new VerificationContextRequestConverter();

    @Test
    public void shouldPopulateChannel() {
        final Channel channel = mock(Channel.class);
        final VerificationContextRequest request = VerificationContextRequest.builder()
                .channel(channel)
                .build();

        final VerificationContextServiceRequest serviceRequest = converter.toServiceRequest(request, identity);

        assertThat(serviceRequest.getChannel()).isEqualTo(channel);
    }

    @Test
    public void shouldPopulateActivity() {
        final Activity activity = mock(Activity.class);
        final VerificationContextRequest request = VerificationContextRequest.builder()
                .activity(activity)
                .build();

        final VerificationContextServiceRequest serviceRequest = converter.toServiceRequest(request, identity);

        assertThat(serviceRequest.getActivity()).isEqualTo(activity);
    }

    @Test
    public void shouldPopulateProvidedAlias() {
        final Alias providedAlias = mock(Alias.class);
        final VerificationContextRequest request = VerificationContextRequest.builder()
                .providedAlias(providedAlias)
                .build();

        final VerificationContextServiceRequest serviceRequest = converter.toServiceRequest(request, identity);

        assertThat(serviceRequest.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    public void shouldPopulateIdentity() {
        final VerificationContextRequest request = mock(VerificationContextRequest.class);

        final VerificationContextServiceRequest serviceRequest = converter.toServiceRequest(request, identity);

        assertThat(serviceRequest.getIdentity()).isEqualTo(identity);
    }

}
