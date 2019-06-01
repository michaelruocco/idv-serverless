package uk.co.mruoc.idv.core.verificationcontext.model.result;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class RegisterAttemptsRequestTest {

    @Test
    public void shouldReturnNewAttempts() {
        final VerificationMethodResults newResults = mock(VerificationMethodResults.class);

        final RegisterAttemptsRequest request = RegisterAttemptsRequest.builder()
                .newResults(newResults)
                .build();

        assertThat(request.getNewResults()).isEqualTo(newResults);
    }

    @Test
    public void shouldReturnExistingAttempts() {
        final VerificationMethodResults existingAttempts = mock(VerificationMethodResults.class);

        final RegisterAttemptsRequest request = RegisterAttemptsRequest.builder()
                .existingResults(existingAttempts)
                .build();

        assertThat(request.getExistingResults()).isEqualTo(existingAttempts);
    }

    @Test
    public void shouldReturnContext() {
        final VerificationContext context = mock(VerificationContext.class);

        final RegisterAttemptsRequest request = RegisterAttemptsRequest.builder()
                .context(context)
                .build();

        assertThat(request.getContext()).isEqualTo(context);
    }

}
