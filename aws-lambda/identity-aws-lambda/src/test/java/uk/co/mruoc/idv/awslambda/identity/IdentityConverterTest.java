package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.jsonapi.identity.IdentityJsonApiDocument;


import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class IdentityConverterTest {

    private final ObjectMapper mapper = mock(ObjectMapper.class);

    private final IdentityConverter converter = new IdentityConverter(mapper);

    @Test
    public void shouldThrowExceptionIfDocumentCannotBeConvertedToJsonString() throws JsonProcessingException {
        final IdentityJsonApiDocument document = mock(IdentityJsonApiDocument.class);
        doThrow(JsonProcessingException.class).when(mapper).writeValueAsString(document);

        final Throwable cause = catchThrowable(() -> converter.toResponseEvent(document));

        assertThat(cause).isInstanceOf(UncheckedIOException.class)
                .hasCauseInstanceOf(JsonProcessingException.class);
    }


    @Test
    public void shouldConvertToJsonApiDocument() {
        final Identity identity = mock(Identity.class);

        final IdentityJsonApiDocument document = converter.toJsonApiDocument(identity);

        assertThat(document.getIdentity()).isEqualTo(identity);
    }

    @Test
    public void shouldPopulateJsonApiDocumentBodyToResponseEvent() throws JsonProcessingException {
        final String body = "body";
        final IdentityJsonApiDocument document = mock(IdentityJsonApiDocument.class);
        given(mapper.writeValueAsString(document)).willReturn(body);

        final APIGatewayProxyResponseEvent event = converter.toResponseEvent(document);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldConvertIdentityToResponse() throws JsonProcessingException {
        final String body = "body";
        final Identity identity = mock(Identity.class);
        given(mapper.writeValueAsString(any(IdentityJsonApiDocument.class))).willReturn(body);

        final APIGatewayProxyResponseEvent event = converter.toResponseEvent(identity);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldPopulateOkStatusCode() {
        final IdentityJsonApiDocument document = mock(IdentityJsonApiDocument.class);

        final APIGatewayProxyResponseEvent event = converter.toResponseEvent(document);

        assertThat(event.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldNotPopulateHeaders() {
        final IdentityJsonApiDocument document = mock(IdentityJsonApiDocument.class);

        final APIGatewayProxyResponseEvent event = converter.toResponseEvent(document);

        assertThat(event.getHeaders()).isNull();
    }

}
