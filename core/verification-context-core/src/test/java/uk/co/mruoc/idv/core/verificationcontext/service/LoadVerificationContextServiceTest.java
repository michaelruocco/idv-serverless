package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.service.LoadVerificationContextService.VerificationContextNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class LoadVerificationContextServiceTest {

    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final LoadVerificationContextService service = LoadVerificationContextService.builder()
            .dao(dao)
            .build();

    @Test
    public void shouldThrowExceptionIfVerificationContextNotFound() {
        final UUID contextId = UUID.randomUUID();
        given(dao.load(contextId)).willReturn(Optional.empty());

        final Throwable thrown = catchThrowable(() -> service.load(contextId));

        assertThat(thrown).isInstanceOf(VerificationContextNotFoundException.class)
                .hasMessage(contextId.toString());
    }

    @Test
    public void shouldLoadVerificationContext() {
        final UUID contextId = UUID.randomUUID();
        final VerificationContext expectedContext = mock(VerificationContext.class);
        given(dao.load(contextId)).willReturn(Optional.of(expectedContext));

        final VerificationContext context = service.load(contextId);

        assertThat(context).isEqualTo(expectedContext);
    }

}
