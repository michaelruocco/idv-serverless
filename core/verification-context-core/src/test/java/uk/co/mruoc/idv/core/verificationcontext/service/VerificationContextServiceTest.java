package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.service.UuidGenerator;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.As3Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VerificationContextServiceTest {

    private static final Duration FIVE_MINUTES = Duration.ofMinutes(5);

    private final UuidGenerator idGenerator = mock(UuidGenerator.class);
    private final TimeService timeService = mock(TimeService.class);
    private final ExpiryCalculator expiryCalculator = mock(ExpiryCalculator.class);
    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final VerificationContextService service = VerificationContextService.builder()
            .idGenerator(idGenerator)
            .timeService(timeService)
            .expiryCalculator(expiryCalculator)
            .dao(dao)
            .build();

    @Test
    public void shouldCreateVerificationContext() {
        final Instant now = Instant.now();
        given(timeService.now()).willReturn(now);

        final UUID contextId = UUID.randomUUID();
        given(idGenerator.randomUuid()).willReturn(contextId);

        final Instant expiry = now.plus(FIVE_MINUTES);
        given(expiryCalculator.calculateExpiry(now)).willReturn(expiry);

        final Alias inputAlias = new IdvIdAlias();
        final Channel channel = new As3Channel();
        final Identity identity = Identity.withAliases(inputAlias);
        final Activity activity = new LoginActivity(now);
        final VerificationContextRequest request = VerificationContextRequest.builder()
                .channel(channel)
                .inputAlias(inputAlias)
                .identity(identity)
                .activity(activity)
                .build();

        final VerificationContext context = service.create(request);

        assertThat(context.getId()).isEqualTo(contextId);
        assertThat(context.getChannel()).isEqualTo(channel);
        assertThat(context.getInputAlias()).isEqualTo(inputAlias);
        assertThat(context.getIdentity()).isEqualTo(identity);
        assertThat(context.getActivity()).isEqualTo(activity);
        assertThat(context.getCreated()).isEqualTo(now);
        assertThat(context.getExpiry()).isEqualTo(expiry);
    }

}
