package uk.co.mruoc.idv.awslambda.verificationcontext.result;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.apache.http.HttpStatus;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.ExceptionConverter;
import uk.co.mruoc.idv.awslambda.verificationcontext.result.VerificationMethodResultsExtractor.InvalidVerificationMethodResultsException;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class PostVerificationResultHandlerTest {

    private final VerificationMethodResultsExtractor resultsExtractor = mock(VerificationMethodResultsExtractor.class);
    private final VerificationResultService service = mock(VerificationResultService.class);
    private final VerificationMethodResultsResponseFactory responseFactory = mock(VerificationMethodResultsResponseFactory.class);
    private final ExceptionConverter exceptionConverter = mock(ExceptionConverter.class);
    private final StatusCalculator statusCalculator = mock(StatusCalculator.class);

    private final Context lambdaContext = mock(Context.class);

    private final PostVerificationResultHandler handler = PostVerificationResultHandler.builder()
            .resultsExtractor(resultsExtractor)
            .service(service)
            .responseFactory(responseFactory)
            .exceptionConverter(exceptionConverter)
            .statusCalculator(statusCalculator)
            .build();

    @Test
    public void shouldReturnErrorIfExceptionIsThrown() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        final Exception exception = new InvalidVerificationMethodResultsException(new Exception());
        doThrow(exception).when(resultsExtractor).extractRequest(request);

        final APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        given(exceptionConverter.toResponse(exception)).willReturn(expectedResponse);

        final APIGatewayProxyResponseEvent response = handler.handleRequest(request, lambdaContext);

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void shouldSaveVerificationResults() {
        final APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        final VerificationMethodResults inputResults = mock(VerificationMethodResults.class);
        given(resultsExtractor.extractRequest(requestEvent)).willReturn(inputResults);

        final VerificationMethodResults createdResults = mock(VerificationMethodResults.class);
        given(service.upsert(inputResults)).willReturn(createdResults);

        final int statusCode = HttpStatus.SC_CREATED;
        given(statusCalculator.calculate(inputResults, createdResults)).willReturn(statusCode);

        final APIGatewayProxyResponseEvent expectedResponseEvent = new APIGatewayProxyResponseEvent();
        given(responseFactory.toResponseEvent(statusCode, createdResults)).willReturn(expectedResponseEvent);

        final APIGatewayProxyResponseEvent responseEvent = handler.handleRequest(requestEvent, lambdaContext);

        assertThat(responseEvent).isEqualTo(expectedResponseEvent);
    }

}
