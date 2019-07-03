package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.lockoutdecision.RegisterAttemptsRequestExtractor.InvalidRegisterAttemptsRequestException;
import uk.co.mruoc.idv.core.verificationattempts.model.RegisterAttemptRequest;
import uk.co.mruoc.idv.core.verificationattempts.model.RegisterAttemptsRequest;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.lockoutstate.RegisterAttemptsRequestDocument;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class RegisterAttemptsRequestExtractorTest {

    private final JsonConverter converter = mock(JsonConverter.class);

    private final RegisterAttemptsRequestExtractor extractor = new RegisterAttemptsRequestExtractor(converter);

    @Test
    public void shouldThrowExceptionIfRequestBodyCannotBeParsed() {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        doThrow(JsonConverter.JsonConversionException.class).when(converter).toObject(body, RegisterAttemptsRequestDocument.class);

        final Throwable cause = catchThrowable(() -> extractor.extractRequest(event));

        assertThat(cause).isInstanceOf(InvalidRegisterAttemptsRequestException.class)
                .hasCauseInstanceOf(JsonConverter.JsonConversionException.class);
    }

    @Test
    public void shouldThrowExceptionIfRequestBodyHasEmptyAttemptsArray() {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        final RegisterAttemptsRequestDocument document = mock(RegisterAttemptsRequestDocument.class);
        final RegisterAttemptsRequest request = mock(RegisterAttemptsRequest.class);
        given(document.getRequest()).willReturn(request);
        given(request.isEmpty()).willReturn(true);
        given(converter.toObject(body, RegisterAttemptsRequestDocument.class)).willReturn(document);

        final Throwable cause = catchThrowable(() -> extractor.extractRequest(event));

        assertThat(cause).isInstanceOf(InvalidRegisterAttemptsRequestException.class)
                .hasMessage("results array must not be empty");
    }

    @Test
    public void shouldReturnRegisterAttemptsRequest() {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        final RegisterAttemptsRequestDocument document = mock(RegisterAttemptsRequestDocument.class);
        final RegisterAttemptsRequest expectedRequest = mock(RegisterAttemptsRequest.class);
        final Collection<RegisterAttemptRequest> attempts = Collections.singleton(mock(RegisterAttemptRequest.class));
        given(document.getRequest()).willReturn(expectedRequest);
        given(expectedRequest.isEmpty()).willReturn(false);
        given(converter.toObject(body, RegisterAttemptsRequestDocument.class)).willReturn(document);

        final RegisterAttemptsRequest request = extractor.extractRequest(event);

        assertThat(request).isEqualTo(expectedRequest);
    }

}
