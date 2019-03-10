package uk.co.mruoc.idv.core.verificationcontext.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAlias;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CreateVerificationContextRequestTest {

    @Test
    public void shouldReturnChannel() {
        final Channel channel = mock(Channel.class);

        final CreateVerificationContextRequest request = CreateVerificationContextRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    public void shouldReturnAlias() {
        final Alias alias = mock(Alias.class);

        final CreateVerificationContextRequest request = CreateVerificationContextRequest.builder()
                .alias(alias)
                .build();

        assertThat(request.getAlias()).isEqualTo(alias);
    }

    @Test
    public void shouldReturnActivity() {
        final Activity activity = mock(Activity.class);

        final CreateVerificationContextRequest request = CreateVerificationContextRequest.builder()
                .activity(activity)
                .build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    public void shouldPrintAllValues() {
        final Instant timestamp = Instant.parse("2019-03-10T12:53:57.547Z");
        final CreateVerificationContextRequest request = CreateVerificationContextRequest.builder()
                .channel(new As3Channel())
                .alias(new UkcCardholderIdAlias("12345678"))
                .activity(new LoginActivity(timestamp))
                .build();

        assertThat(request.toString()).isEqualTo("CreateVerificationContextRequest(" +
                "channel=DefaultChannel(id=AS3), " +
                "alias=DefaultAlias(type=DefaultAliasType(name=UKC_CARDHOLDER_ID), format=CLEAR_TEXT, value=12345678), " +
                "activity=DefaultActivity(type=LOGIN, timestamp=2019-03-10T12:53:57.547Z, genericProperties={}))");
    }

}
