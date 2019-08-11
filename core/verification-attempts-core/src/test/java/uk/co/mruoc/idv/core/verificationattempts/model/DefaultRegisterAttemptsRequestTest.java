package uk.co.mruoc.idv.core.verificationattempts.model;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultRegisterAttemptsRequestTest {

    @Test
    public void shouldReturnVerificationId() {
        final Collection<RegisterAttemptRequest> attempts = Collections.emptyList();

        final RegisterAttemptsRequest request = DefaultRegisterAttemptsRequest.builder()
                .attempts(attempts)
                .build();

        assertThat(request).containsExactlyElementsOf(attempts);
    }

    @Test
    public void shouldHaveNoArgsConstructorForJackson() {
        final RegisterAttemptsRequest request = new DefaultRegisterAttemptsRequest();

        assertThat(request).isNotNull();
    }

}