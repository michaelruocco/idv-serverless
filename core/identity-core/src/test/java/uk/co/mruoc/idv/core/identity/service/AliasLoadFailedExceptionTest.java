package uk.co.mruoc.idv.core.identity.service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AliasLoadFailedExceptionTest {

    @Test
    public void shouldHaveNullCauseAndMessageIfNotProvided() {
        final Throwable exception = new AliasLoadFailedException();

        assertThat(exception.getCause()).isNull();
        assertThat(exception.getMessage()).isNull();
    }

    @Test
    public void shouldReturnMessage() {
        final String message = "message";

        final Throwable exception = new AliasLoadFailedException(message);

        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    public void shouldReturnCause() {
        final Throwable cause = new Exception("cause");

        final Throwable exception = new AliasLoadFailedException(cause);

        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    public void shouldReturnCauseAndMessage() {
        final Throwable cause = new Exception("cause");
        final String message = "message";

        final Throwable exception = new AliasLoadFailedException(message, cause);

        assertThat(exception.getCause()).isEqualTo(cause);
        assertThat(exception.getMessage()).isEqualTo(message);
    }

}
