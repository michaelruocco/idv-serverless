package uk.co.mruoc.idv.awslambda.authorizer.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.authorizer.handler.GenerateTokenRequestExtractor.InvalidGenerateTokenRequestException;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverter.JsonConversionException;
import uk.co.mruoc.idv.jsonapi.authorizer.GenerateTokenRequestDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class GenerateTokenRequestExtractorTest {

    private final JsonConverter converter = mock(JsonConverter.class);

    private final GenerateTokenRequestExtractor extractor = new GenerateTokenRequestExtractor(converter);

    @Test
    public void shouldThrowExceptionIfRequestBodyCannotBeParsed() {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        doThrow(JsonConversionException.class).when(converter).toObject(body, GenerateTokenRequestDocument.class);

        final Throwable cause = catchThrowable(() -> extractor.extractRequest(event));

        assertThat(cause).isInstanceOf(InvalidGenerateTokenRequestException.class)
                .hasCauseInstanceOf(JsonConversionException.class);
    }

    @Test
    public void shouldReturnContextFromDocumentParsedFromRequestBody() {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        final GenerateTokenRequestDocument document = mock(GenerateTokenRequestDocument.class);
        given(converter.toObject(body, GenerateTokenRequestDocument.class)).willReturn(document);
        final GenerateTokenRequest expectedRequest = mock(GenerateTokenRequest.class);
        given(document.getRequest()).willReturn(expectedRequest);

        final GenerateTokenRequest request = extractor.extractRequest(event);

        assertThat(request).isEqualTo(expectedRequest);
    }
}
