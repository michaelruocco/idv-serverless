package uk.co.mruoc.idv.awslambda.authorizer.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.core.authorizer.model.TokenResponse;
import uk.co.mruoc.idv.core.service.UuidGenerator;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.authorizer.GenerateTokenResponseDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class GenerateTokenResponseFactoryTest {

    private static final int CREATED_STATUS_CODE = 201;

    private final JsonConverter converter = mock(JsonConverter.class);
    private final UuidGenerator uuidGenerator = mock(UuidGenerator.class);

    private final GenerateTokenResponseFactory factory = new GenerateTokenResponseFactory(converter, uuidGenerator);

    @Test
    public void shouldConvertToJsonApiDocument() {
        final TokenResponse tokenResponse = mock(TokenResponse.class);

        final GenerateTokenResponseDocument document = factory.toResponseDocument(tokenResponse);

        assertThat(document.getTokenResponse()).isEqualTo(tokenResponse);
    }

    @Test
    public void shouldPopulateJsonApiDocumentBodyToResponseEvent() {
        final String body = "body";
        final GenerateTokenResponseDocument document = mock(GenerateTokenResponseDocument.class);
        given(converter.toJson(document)).willReturn(body);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(document);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldConvertTokenResponseToResponseBody() {
        final String body = "body";
        final TokenResponse tokenResponse = mock(TokenResponse.class);
        given(converter.toJson(any(GenerateTokenResponseDocument.class))).willReturn(body);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(tokenResponse);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldPopulateStatusCode() {
        final GenerateTokenResponseDocument document = mock(GenerateTokenResponseDocument.class);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(document);

        assertThat(event.getStatusCode()).isEqualTo(CREATED_STATUS_CODE);
    }

    @Test
    public void shouldNotPopulateHeaders() {
        final GenerateTokenResponseDocument document = mock(GenerateTokenResponseDocument.class);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(document);

        assertThat(event.getHeaders()).isNull();
    }

}
