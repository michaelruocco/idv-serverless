package uk.co.mruoc.idv.awslambda.verificationcontext.result;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.verificationcontext.result.VerificationMethodResultsExtractor.InvalidVerificationMethodResultsException;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.json.JsonConverter.JsonConversionException;
import uk.co.mruoc.idv.jsonapi.verificationcontext.result.VerificationResultRequestDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class VerificationMethodResultsExtractorTest {

    private final JsonConverter converter = mock(JsonConverter.class);

    private final VerificationMethodResultsExtractor extractor = new VerificationMethodResultsExtractor(converter);

    @Test
    public void shouldThrowExceptionIfRequestBodyCannotBeParsed() {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        doThrow(JsonConversionException.class).when(converter).toObject(body, VerificationResultRequestDocument.class);

        final Throwable cause = catchThrowable(() -> extractor.extractRequest(event));

        assertThat(cause).isInstanceOf(InvalidVerificationMethodResultsException.class)
                .hasCauseInstanceOf(JsonConversionException.class);
    }

    @Test
    public void shouldReturnContextFromDocumentParsedFromRequestBody() {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        final VerificationResultRequestDocument document = mock(VerificationResultRequestDocument.class);
        given(converter.toObject(body, VerificationResultRequestDocument.class)).willReturn(document);
        final VerificationMethodResults expectedResults = mock(VerificationMethodResults.class);
        given(document.getResults()).willReturn(expectedResults);

        final VerificationMethodResults results = extractor.extractRequest(event);

        assertThat(results).isEqualTo(expectedResults);
    }

    @Test
    public void shouldThrowExceptionIfResultsListIsEmpty() {
        final String body = "body";
        final APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(body);
        final VerificationResultRequestDocument document = mock(VerificationResultRequestDocument.class);
        given(converter.toObject(body, VerificationResultRequestDocument.class)).willReturn(document);
        final VerificationMethodResults expectedResults = mock(VerificationMethodResults.class);
        given(document.getResults()).willReturn(expectedResults);
        given(expectedResults.isEmpty()).willReturn(true);

        final Throwable cause = catchThrowable(() -> extractor.extractRequest(event));

        assertThat(cause).isInstanceOf(InvalidVerificationMethodResultsException.class)
                .hasMessage("results array must not be empty");
    }

}
