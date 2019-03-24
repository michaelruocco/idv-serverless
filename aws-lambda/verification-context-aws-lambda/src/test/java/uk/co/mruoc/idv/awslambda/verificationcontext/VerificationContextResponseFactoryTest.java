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

public class VerificationContextResponseFactoryTest {

    private static final int STATUS_CODE = 200;

    private final ObjectMapper mapper = mock(ObjectMapper.class);

    private final VerificationContextResponseFactory factory = new VerificationContextResponseFactory(STATUS_CODE, mapper);

    @Test
    public void shouldThrowExceptionIfDocumentCannotBeConvertedToJsonString() throws JsonProcessingException {
        final VerificationContextResponseDocument document = mock(VerificationContextResponseDocument.class);
        doThrow(JsonProcessingException.class).when(mapper).writeValueAsString(document);

        final Throwable cause = catchThrowable(() -> factory.toResponseEvent(document));

        assertThat(cause).isInstanceOf(UncheckedIOException.class)
                .hasCauseInstanceOf(JsonProcessingException.class);
    }

    @Test
    public void shouldConvertToJsonApiDocument() {
        final VerificationContext context = mock(VerificationContext.class);

        final VerificationContextResponseDocument document = factory.toResponseDocument(context);

        assertThat(document.getContext()).isEqualTo(context);
    }

    @Test
    public void shouldPopulateJsonApiDocumentBodyToResponseEvent() throws JsonProcessingException {
        final String body = "body";
        final VerificationContextResponseDocument document = mock(VerificationContextResponseDocument.class);
        given(mapper.writeValueAsString(document)).willReturn(body);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(document);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldConvertIdentityToResponse() throws JsonProcessingException {
        final String body = "body";
        final VerificationContext context = mock(VerificationContext.class);
        given(mapper.writeValueAsString(any(VerificationContextResponseDocument.class))).willReturn(body);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(context);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldPopulateStatusCode() {
        final VerificationContextResponseDocument document = mock(VerificationContextResponseDocument.class);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(document);

        assertThat(event.getStatusCode()).isEqualTo(STATUS_CODE);
    }

    @Test
    public void shouldNotPopulateHeaders() {
        final VerificationContextResponseDocument document = mock(VerificationContextResponseDocument.class);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(document);

        assertThat(event.getHeaders()).isNull();
    }

}
