package uk.co.mruoc.idv.awslambda.verificationcontext;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextResponseDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class VerificationContextOkResponseFactoryTest {

    private static final int CREATED_STATUS_CODE = 200;
    
    private final ObjectMapper mapper = mock(ObjectMapper.class);

    private final VerificationContextResponseFactory factory = new VerificationContextOkResponseFactory(mapper);

    @Test
    public void shouldPopulateCreatedStatusCode() {
        final VerificationContextResponseDocument document = mock(VerificationContextResponseDocument.class);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(document);

        assertThat(event.getStatusCode()).isEqualTo(CREATED_STATUS_CODE);
    }

}
