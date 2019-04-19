package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationMethodsService.EligibilityHandlerNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

public class EligibilityHandlerNotFoundExceptionTest {

    private static final String CHANNEL_ID = "channelId";
    private static final String METHOD_NAME = "methodName";

    private final EligibilityHandlerNotFoundException exception = new EligibilityHandlerNotFoundException(CHANNEL_ID, METHOD_NAME);

    @Test
    public void shouldReturnChannelId() {
        assertThat(exception.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    public void shouldReturnMethodName() {
        assertThat(exception.getMethodName()).isEqualTo(METHOD_NAME);
    }

    @Test
    public void shouldReturnMessage() {
        final String expectedMessage = "eligibility handler for channel channelId and method methodName not found";

        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

}
