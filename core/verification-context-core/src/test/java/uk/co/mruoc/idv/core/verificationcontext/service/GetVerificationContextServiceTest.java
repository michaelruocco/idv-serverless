package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.core.service.TimeService;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService.VerificationContextExpiredException;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService.VerificationContextNotFoundException;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class GetVerificationContextServiceTest {

    private static final Instant NOW = Instant.now();
    private static final Duration TEN_MINUTES = Duration.ofMinutes(10);

    private final VerificationContextDao dao = mock(VerificationContextDao.class);
    private final TimeService timeService = mock(TimeService.class);

    private final GetVerificationContextService service = GetVerificationContextService.builder()
            .dao(dao)
            .timeService(timeService)
            .build();

    @Before
    public void setUp() {
        given(timeService.now()).willReturn(NOW);
    }

    @Test
    public void shouldThrowExceptionIfVerificationContextNotFound() {
        final UUID contextId = UUID.randomUUID();
        given(dao.load(contextId)).willReturn(Optional.empty());

        final Throwable thrown = catchThrowable(() -> service.load(contextId));

        assertThat(thrown).isInstanceOf(VerificationContextNotFoundException.class)
                .hasMessage(contextId.toString());
    }

    @Test
    public void shouldThrowExceptionIfVerificationContextHasExpired() {
        final UUID contextId = UUID.randomUUID();
        final VerificationContext context = mock(VerificationContext.class);
        given(context.getId()).willReturn(contextId);
        given(context.getExpiry()).willReturn(NOW.minus(TEN_MINUTES));
        given(dao.load(contextId)).willReturn(Optional.of(context));

        final Throwable thrown = catchThrowable(() -> service.load(contextId));

        assertThat(thrown).isInstanceOf(VerificationContextExpiredException.class)
                .hasMessage(contextId.toString());
    }

    @Test
    public void shouldLoadVerificationContextIfNotExpired() {
        final UUID contextId = UUID.randomUUID();
        final VerificationContext expectedContext = mock(VerificationContext.class);
        given(expectedContext.getExpiry()).willReturn(NOW.plus(TEN_MINUTES));
        given(dao.load(contextId)).willReturn(Optional.of(expectedContext));

        final VerificationContext context = service.load(contextId);

        assertThat(context).isEqualTo(expectedContext);
    }

}
