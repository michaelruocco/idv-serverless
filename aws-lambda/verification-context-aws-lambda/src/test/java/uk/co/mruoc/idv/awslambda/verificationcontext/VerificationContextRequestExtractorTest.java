package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.verificationcontext.VerificationContextRequestExtractor.InvalidVerificationContextRequestException;
import uk.co.mruoc.idv.core.verificationcontext.model.AbstractVerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverter.JsonConversionException;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextRequestDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class VerificationContextRequestExtractorTest {

    private final JsonConverter converter = mock(JsonConverter.class);

    private final VerificationContextRequestExtractor extractor = new VerificationContextRequestExtractor(converter);

    @Test
    public void shouldThrowExceptionIfRequestBodyCannotBeParsed() {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        doThrow(JsonConversionException.class).when(converter).toObject(body, VerificationContextRequestDocument.class);

        final Throwable cause = catchThrowable(() -> extractor.extractRequest(event));

        assertThat(cause).isInstanceOf(InvalidVerificationContextRequestException.class)
                .hasCauseInstanceOf(JsonConversionException.class);
    }

    @Test
    public void shouldReturnContextFromDocumentParsedFromRequestBody() {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        final VerificationContextRequestDocument document = mock(VerificationContextRequestDocument.class);
        given(converter.toObject(body, VerificationContextRequestDocument.class)).willReturn(document);
        final AbstractVerificationContextRequest expectedRequest = mock(AbstractVerificationContextRequest.class);
        given(document.getRequest()).willReturn(expectedRequest);

        final VerificationContextRequest request = extractor.extractRequest(event);

        assertThat(request).isEqualTo(expectedRequest);
    }

}
