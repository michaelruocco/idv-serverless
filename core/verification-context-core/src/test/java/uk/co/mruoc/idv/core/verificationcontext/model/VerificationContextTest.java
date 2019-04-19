package uk.co.mruoc.idv.core.verificationcontext.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.DefaultVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VerificationContextTest {

    @Test
    public void shouldReturnId() {
        final UUID id = UUID.randomUUID();

        final VerificationContext context = VerificationContext.builder()
                .id(id)
                .build();

        assertThat(context.getId()).isEqualTo(id);
    }

    @Test
    public void shouldReturnChannel() {
        final Channel channel = mock(Channel.class);

        final VerificationContext context = VerificationContext.builder()
                .channel(channel)
                .build();

        assertThat(context.getChannel()).isEqualTo(channel);
    }

    @Test
    public void shouldReturnProvidedAlias() {
        final Alias providedAlias = mock(Alias.class);

        final VerificationContext context = VerificationContext.builder()
                .providedAlias(providedAlias)
                .build();

        assertThat(context.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    public void shouldReturnIdentity() {
        final Identity identity = mock(Identity.class);

        final VerificationContext context = VerificationContext.builder()
                .identity(identity)
                .build();

        assertThat(context.getIdentity()).isEqualTo(identity);
    }

    @Test
    public void shouldReturnIdvId() {
        final UUID idvId = UUID.randomUUID();
        final Identity identity = mock(Identity.class);
        given(identity.getIdvId()).willReturn(idvId);

        final VerificationContext context = VerificationContext.builder()
                .identity(identity)
                .build();

        assertThat(context.getIdvId()).isEqualTo(idvId);
    }

    @Test
    public void shouldReturnActivity() {
        final Activity activity = mock(Activity.class);

        final VerificationContext request = VerificationContext.builder()
                .activity(activity)
                .build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    public void shouldReturnCreated() {
        final Instant created = Instant.now();

        final VerificationContext request = VerificationContext.builder()
                .created(created)
                .build();

        assertThat(request.getCreated()).isEqualTo(created);
    }

    @Test
    public void shouldReturnExpiry() {
        final Instant expiry = Instant.now();

        final VerificationContext request = VerificationContext.builder()
                .expiry(expiry)
                .build();

        assertThat(request.getExpiry()).isEqualTo(expiry);
    }

    @Test
    public void shouldPrintAllValues() {
        final UUID id = UUID.fromString("cbe28548-edc5-492b-94d4-6f013b92cece");
        final Instant timestamp = Instant.parse("2019-03-10T12:53:57.547Z");
        final Alias alias = new IdvIdAlias(UUID.fromString("1ab2141d-a910-4bf8-99a0-efedfbf34b6a"));
        final Instant created = timestamp.plusSeconds(5);
        final Instant expiry = created.plus(5, ChronoUnit.MINUTES);
        final Collection<VerificationMethodSequence> sequences = Collections.singleton(new VerificationMethodSequence(new DefaultVerificationMethod("method")));
        final VerificationContext request = VerificationContext.builder()
                .id(id)
                .channel(new DefaultChannel("CHANNEL_ID"))
                .providedAlias(alias)
                .identity(Identity.withAliases(alias))
                .activity(new LoginActivity(timestamp))
                .created(created)
                .expiry(expiry)
                .sequences(sequences)
                .build();

        assertThat(request.toString()).isEqualTo("VerificationContext(" +
                "id=cbe28548-edc5-492b-94d4-6f013b92cece, " +
                "channel=DefaultChannel(id=CHANNEL_ID), " +
                "providedAlias=DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=1ab2141d-a910-4bf8-99a0-efedfbf34b6a), " +
                "identity=Identity(aliases=Aliases(aliases=[DefaultAlias(type=DefaultAliasType(name=IDV_ID), format=CLEAR_TEXT, value=1ab2141d-a910-4bf8-99a0-efedfbf34b6a)])), " +
                "activity=DefaultActivity(type=LOGIN, timestamp=2019-03-10T12:53:57.547Z, properties={}), " +
                "created=2019-03-10T12:54:02.547Z, " +
                "expiry=2019-03-10T12:59:02.547Z, " +
                "sequences=[VerificationMethodSequence(name=method, failureStrategy=IMMEDIATE, methods=[DefaultVerificationMethod(" +
                "name=method, duration=300000, status=AVAILABLE, maxAttempts=1, properties={})])])");
    }

}
