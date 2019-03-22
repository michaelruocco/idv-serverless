package uk.co.mruoc.idv.awslambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import uk.co.mruoc.jsonapi.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.JsonApiErrorItem;

import java.io.UncheckedIOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ExceptionConverterTest {

    private static final int STATUS_CODE = 500;

    private final Exception exception = mock(Exception.class);
    private final JsonApiErrorDocument document = mock(JsonApiErrorDocument.class);
    private final JsonApiErrorItem errorItem = mock(JsonApiErrorItem.class);


    private final ObjectMapper mapper = mock(ObjectMapper.class);
    private final ErrorHandlerDelegator errorHandler = mock(ErrorHandlerDelegator.class);

    private final ExceptionConverter converter = ExceptionConverter.builder()
            .mapper(mapper)
            .errorHandler(errorHandler)
            .build();

    @Before
    public void setUp() {
        given(errorHandler.toDocument(exception)).willReturn(document);
        given(document.getErrors()).willReturn(Collections.singletonList(errorItem));
        given(errorItem.getStatusCode()).willReturn(STATUS_CODE);
    }

    @Test
    public void shouldThrowExceptionIfJsonConversionThrowsException() throws JsonProcessingException {
        doThrow(JsonProcessingException.class).when(mapper).writeValueAsString(document);

        final Throwable cause = catchThrowable(() -> converter.toResponse(exception));

        assertThat(cause).isInstanceOf(UncheckedIOException.class)
                .hasCauseInstanceOf(JsonProcessingException.class);
    }

    @Test
    public void shouldPopulateBodyWithErrorDocument() throws JsonProcessingException {
        final String expectedBody = "body";
        given(mapper.writeValueAsString(document)).willReturn(expectedBody);

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
