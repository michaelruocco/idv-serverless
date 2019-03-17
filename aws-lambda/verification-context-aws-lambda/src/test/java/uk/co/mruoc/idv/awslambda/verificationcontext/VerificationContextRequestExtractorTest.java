package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextRequestDocument;

import java.io.IOException;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class VerificationContextRequestExtractorTest {

    private final ObjectMapper mapper = mock(ObjectMapper.class);

    private final VerificationContextRequestExtractor extractor = new VerificationContextRequestExtractor(mapper);

    @Test
    public void shouldThrowExceptionIfRequestBodyCannotBeParsed() throws IOException {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        doThrow(IOException.class).when(mapper).readValue(body, VerificationContextRequestDocument.class);

        final Throwable cause = catchThrowable(() -> extractor.extractRequest(event));

        assertThat(cause).isInstanceOf(UncheckedIOException.class)
                .hasCauseInstanceOf(IOException.class);
    }

    @Test
    public void shouldReturnContextFromDocumentParsedFromRequestBody() throws IOException {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        final VerificationContextRequestDocument document = mock(VerificationContextRequestDocument.class);
        given(mapper.readValue(body, VerificationContextRequestDocument.class)).willReturn(document);
        final VerificationContextRequest expectedRequest = mock(VerificationContextRequest.class);
        given(document.getRequest()).willReturn(expectedRequest);

        final VerificationContextRequest request = extractor.extractRequest(event);

        assertThat(request).isEqualTo(expectedRequest);
    }

}
