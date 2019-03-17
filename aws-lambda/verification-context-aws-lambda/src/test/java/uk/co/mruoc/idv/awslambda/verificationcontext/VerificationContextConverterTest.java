package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextResponseDocument;

import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class VerificationContextConverterTest {

    private final ObjectMapper mapper = mock(ObjectMapper.class);

    private final VerificationContextConverter converter = new VerificationContextConverter(mapper);

    @Test
    public void shouldThrowExceptionIfDocumentCannotBeConvertedToJsonString() throws JsonProcessingException {
        final VerificationContextResponseDocument document = mock(VerificationContextResponseDocument.class);
        doThrow(JsonProcessingException.class).when(mapper).writeValueAsString(document);

        final Throwable cause = catchThrowable(() -> converter.toResponseEvent(document));

        assertThat(cause).isInstanceOf(UncheckedIOException.class)
                .hasCauseInstanceOf(JsonProcessingException.class);
    }

    @Test
    public void shouldConvertToJsonApiDocument() {
        final VerificationContext context = mock(VerificationContext.class);

        final VerificationContextResponseDocument document = converter.toResponseDocument(context);

        assertThat(document.getContext()).isEqualTo(context);
    }

    @Test
    public void shouldPopulateJsonApiDocumentBodyToResponseEvent() throws JsonProcessingException {
        final String body = "body";
        final VerificationContextResponseDocument document = mock(VerificationContextResponseDocument.class);
        given(mapper.writeValueAsString(document)).willReturn(body);

        final APIGatewayProxyResponseEvent event = converter.toResponseEvent(document);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldConvertIdentityToResponse() throws JsonProcessingException {
        final String body = "body";
        final VerificationContext context = mock(VerificationContext.class);
        given(mapper.writeValueAsString(any(VerificationContextResponseDocument.class))).willReturn(body);

        final APIGatewayProxyResponseEvent event = converter.toResponseEvent(context);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldPopulateOkStatusCode() {
        final VerificationContextResponseDocument document = mock(VerificationContextResponseDocument.class);

        final APIGatewayProxyResponseEvent event = converter.toResponseEvent(document);

        assertThat(event.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldNotPopulateHeaders() {
        final VerificationContextResponseDocument document = mock(VerificationContextResponseDocument.class);

        final APIGatewayProxyResponseEvent event = converter.toResponseEvent(document);

        assertThat(event.getHeaders()).isNull();
    }

}
