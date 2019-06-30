package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;
import uk.co.mruoc.idv.json.JsonConverter;
import uk.co.mruoc.idv.jsonapi.lockoutstate.LockoutStateResponseDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class LockoutStateOkResponseFactoryTest {

    private static final int CREATED_STATUS_CODE = 200;
    
    private final JsonConverter converter = mock(JsonConverter.class);

    private final LockoutStateOkResponseFactory factory = new LockoutStateOkResponseFactory(converter);

    @Test
    public void shouldPopulateCreatedStatusCode() {
        final LockoutStateResponseDocument document = mock(LockoutStateResponseDocument.class);

        final APIGatewayProxyResponseEvent event = factory.toResponseEvent(document);

        assertThat(event.getStatusCode()).isEqualTo(CREATED_STATUS_CODE);
    }

}
