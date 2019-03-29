package uk.co.mruoc.idv.awslambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ExceptionConverterTest {

    private static final int STATUS_CODE = 500;

    private final Exception exception = mock(Exception.class);
    private final JsonApiErrorDocument document = mock(JsonApiErrorDocument.class);
    private final JsonApiErrorItem errorItem = mock(JsonApiErrorItem.class);


    private final JsonConverter jsonConverter = mock(JsonConverter.class);
    private final ErrorHandlerDelegator errorHandler = mock(ErrorHandlerDelegator.class);

    private final ExceptionConverter converter = ExceptionConverter.builder()
            .jsonConverter(jsonConverter)
            .errorHandler(errorHandler)
            .build();

    @Before
    public void setUp() {
        given(errorHandler.toDocument(exception)).willReturn(document);
        given(document.getErrors()).willReturn(Collections.singletonList(errorItem));
        given(errorItem.getStatusCode()).willReturn(STATUS_CODE);
    }

    @Test
    public void shouldPopulateBodyWithErrorDocument() {
        final String expectedBody = "body";
        given(jsonConverter.toJson(document)).willReturn(expectedBody);

        final APIGatewayProxyResponseEvent event = converter.toResponse(exception);

        assertThat(event.getBody()).isEqualTo(expectedBody);
    }

    @Test
    public void shouldPopulateStatusWithStatusFromFirstErrorItem() {
        final APIGatewayProxyResponseEvent event = converter.toResponse(exception);

        assertThat(event.getStatusCode()).isEqualTo(STATUS_CODE);
    }

    @Test
    public void shouldNotPopulateHeaders() {
        final APIGatewayProxyResponseEvent event = converter.toResponse(exception);

        assertThat(event.getHeaders()).isNull();
    }

}
