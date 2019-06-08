package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.lockoutdecision.DefaultLockoutStateResponse;
import uk.co.mruoc.idv.jsonapi.lockoutdecision.LockoutStateResponse;
import uk.co.mruoc.idv.jsonapi.lockoutdecision.LockoutStateResponseDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class LockoutStateResponseFactoryTest {

    private static final int STATUS_CODE = 200;

    private final JsonConverter converter = mock(JsonConverter.class);

    private final LockoutStateResponseFactory factory = new LockoutStateResponseFactory(STATUS_CODE, converter);

    @Test
    public void shouldConvertToJsonApiDocument() {
        final LockoutState state = mock(LockoutState.class);

        final LockoutStateResponseDocument document = factory.toResponseDocument(state);

        assertThat(document.getResponse()).isEqualToComparingFieldByFieldRecursively(toResponse(state));
    }

    @Test
    public void shouldPopulateJsonApiDocumentBodyToResponseEvent() {
        final String body = "body";
        final LockoutStateResponseDocument document = mock(LockoutStateResponseDocument.class);
        given(converter.toJson(document)).willReturn(body);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(document);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldConvertVerificationContextToResponse() {
        final String body = "body";
        final LockoutState state = mock(LockoutState.class);
        given(converter.toJson(any(LockoutStateResponseDocument.class))).willReturn(body);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(state);

        assertThat(event.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldPopulateStatusCode() {
        final LockoutStateResponseDocument document = mock(LockoutStateResponseDocument.class);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(document);

        assertThat(event.getStatusCode()).isEqualTo(STATUS_CODE);
    }

    @Test
    public void shouldNotPopulateHeaders() {
        final LockoutStateResponseDocument document = mock(LockoutStateResponseDocument.class);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(document);

        assertThat(event.getHeaders()).isNull();
    }

    private LockoutStateResponse toResponse(final LockoutState state) {
        return DefaultLockoutStateResponse.builder()
                .state(state)
                .build();
    }

}
