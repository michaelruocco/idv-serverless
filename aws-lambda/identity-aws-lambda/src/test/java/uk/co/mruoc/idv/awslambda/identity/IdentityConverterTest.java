package uk.co.mruoc.idv.awslambda.identity;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.identity.IdentityJsonApiDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class IdentityConverterTest {

    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final IdentityConverter converter = new IdentityConverter(jsonConverter);

    @Test
    public void shouldConvertToJsonApiDocument() {
        final Identity identity = mock(Identity.class);

        final IdentityJsonApiDocument document = converter.toJsonApiDocument(identity);

        assertThat(document.getIdentity()).isEqualTo(identity);
    }

    @Test
    public void shouldPopulateJsonApiDocumentBodyToResponseEvent() {
        final String body = "body";
        final IdentityJsonApiDocument document = mock(IdentityJsonApiDocument.class);
        given(jsonConverter.toJson(document)).willReturn(body);

        final APIGatewayProxyResponseEvent event = converter.toResponseEvent(document);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldConvertIdentityToResponse() {
        final String body = "body";
        final Identity identity = mock(Identity.class);
        given(jsonConverter.toJson(any(IdentityJsonApiDocument.class))).willReturn(body);

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
