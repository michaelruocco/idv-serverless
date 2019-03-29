package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextResponseDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class VerificationContextCreatedResponseFactoryTest {

    private static final int CREATED_STATUS_CODE = 201;

    private final JsonConverter converter = mock(JsonConverter.class);

    private final VerificationContextResponseFactory factory = new VerificationContextCreatedResponseFactory(converter);

    @Test
    public void shouldPopulateCreatedStatusCode() {
        final VerificationContextResponseDocument document = mock(VerificationContextResponseDocument.class);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(document);

        assertThat(event.getStatusCode()).isEqualTo(CREATED_STATUS_CODE);
    }

}
