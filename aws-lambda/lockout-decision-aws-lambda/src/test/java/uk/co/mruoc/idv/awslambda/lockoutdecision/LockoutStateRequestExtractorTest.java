package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.lockoutdecision.LockoutStateRequestExtractor.InvalidLockoutStateRequestException;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverter.JsonConversionException;
import uk.co.mruoc.idv.jsonapi.lockoutdecision.LockoutStateRequestDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class LockoutStateRequestExtractorTest {

    private final JsonConverter converter = mock(JsonConverter.class);

    private final LockoutStateRequestExtractor extractor = new LockoutStateRequestExtractor(converter);

    @Test
    public void shouldThrowExceptionIfRequestBodyCannotBeParsed() {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        doThrow(JsonConversionException.class).when(converter).toObject(body, LockoutStateRequestDocument.class);

        final Throwable cause = catchThrowable(() -> extractor.extractRequest(event));

        assertThat(cause).isInstanceOf(InvalidLockoutStateRequestException.class)
                .hasCauseInstanceOf(JsonConversionException.class);
    }

    @Test
    public void shouldReturnContextFromDocumentParsedFromRequestBody() {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        final LockoutStateRequestDocument document = mock(LockoutStateRequestDocument.class);
        given(converter.toObject(body, LockoutStateRequestDocument.class)).willReturn(document);
        final LockoutStateRequest expectedRequest = mock(LockoutStateRequest.class);
        given(document.getRequest()).willReturn(expectedRequest);

        final LockoutStateRequest request = extractor.extractRequest(event);

        assertThat(request).isEqualTo(expectedRequest);
    }

}
