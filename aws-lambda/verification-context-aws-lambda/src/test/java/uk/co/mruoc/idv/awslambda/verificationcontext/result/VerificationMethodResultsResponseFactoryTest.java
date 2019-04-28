package uk.co.mruoc.idv.awslambda.verificationcontext.result;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.verificationcontext.result.VerificationResultResponseDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class VerificationMethodResultsResponseFactoryTest {

    private static final int STATUS_CODE = 200;

    private final JsonConverter converter = mock(JsonConverter.class);

    private final VerificationMethodResultsResponseFactory factory = new VerificationMethodResultsResponseFactory(converter);

    @Test
    public void shouldConvertToJsonApiDocument() {
        final VerificationMethodResults results = mock(VerificationMethodResults.class);

        final VerificationResultResponseDocument document = factory.toResponseDocument(results);

        assertThat(document.getResults()).isEqualTo(results);
    }

    @Test
    public void shouldPopulateJsonApiDocumentBodyToResponseEvent() {
        final String body = "body";
        final VerificationResultResponseDocument document = mock(VerificationResultResponseDocument.class);
        given(converter.toJson(document)).willReturn(body);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(STATUS_CODE, document);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldConvertVerificationContextToResponse() {
        final String body = "body";
        final VerificationMethodResults results = mock(VerificationMethodResults.class);
        given(converter.toJson(any(VerificationResultResponseDocument.class))).willReturn(body);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(STATUS_CODE, results);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldPopulateStatusCode() {
        final VerificationResultResponseDocument document = mock(VerificationResultResponseDocument.class);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(STATUS_CODE, document);

        assertThat(event.getStatusCode()).isEqualTo(STATUS_CODE);
    }

    @Test
    public void shouldNotPopulateHeaders() {
        final VerificationResultResponseDocument document = mock(VerificationResultResponseDocument.class);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(STATUS_CODE, document);

        assertThat(event.getHeaders()).isNull();
    }

}
