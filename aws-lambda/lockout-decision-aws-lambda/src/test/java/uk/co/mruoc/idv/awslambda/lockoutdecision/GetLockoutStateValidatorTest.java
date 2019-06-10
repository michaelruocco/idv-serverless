package uk.co.mruoc.idv.awslambda.lockoutdecision;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.Test;
import uk.co.mruoc.idv.awslambda.lockoutdecision.GetLockoutStateRequestValidator.InvalidLockoutStateRequestException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class GetLockoutStateValidatorTest {

    private final GetLockoutStateRequestValidator validator = new GetLockoutStateRequestValidator();

    @Test
    public void shouldReturnTrueIfAllRequiredParametersArePresent() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("channelId", "CHANNEL");
        parameters.put("activityType", "ACTIVITY_TYPE");
        parameters.put("aliasType", "ALIAS_TYPE");
        parameters.put("aliasValue", "ALIAS_VALUE");
        final APIGatewayProxyRequestEvent event = toEvent(parameters);

        final boolean valid = validator.validate(event);

        assertThat(valid).isTrue();
    }

    @Test
    public void shouldThrowExceptionIfChannelIdIsNotProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("activityType", "ACTIVITY_TYPE");
        parameters.put("aliasType", "ALIAS_TYPE");
        parameters.put("aliasValue", "ALIAS_VALUE");
        final APIGatewayProxyRequestEvent event = toEvent(parameters);

        final Throwable cause = catchThrowable(() -> validator.validate(event));

        assertThat(cause).isInstanceOf(InvalidLockoutStateRequestException.class);
    }

    @Test
    public void shouldThrowExceptionIfActivityTypeIsNotProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("channelId", "CHANNEL");
        parameters.put("aliasType", "ALIAS_TYPE");
        parameters.put("aliasValue", "ALIAS_VALUE");
        final APIGatewayProxyRequestEvent event = toEvent(parameters);

        final Throwable cause = catchThrowable(() -> validator.validate(event));

        assertThat(cause).isInstanceOf(InvalidLockoutStateRequestException.class);
    }

    @Test
    public void shouldThrowExceptionIfAliasValueIsNotProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("channelId", "CHANNEL");
        parameters.put("activityType", "ACTIVITY_TYPE");
        parameters.put("aliasType", "ALIAS_TYPE");
        final APIGatewayProxyRequestEvent event = toEvent(parameters);

        final Throwable cause = catchThrowable(() -> validator.validate(event));

        assertThat(cause).isInstanceOf(InvalidLockoutStateRequestException.class);
    }

    @Test
    public void shouldThrowExceptionIfAliasTypeIsNotProvided() {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("channelId", "CHANNEL");
        parameters.put("activityType", "ACTIVITY_TYPE");
        parameters.put("aliasValue", "ALIAS_VALUE");
        final APIGatewayProxyRequestEvent event = toEvent(parameters);

        final Throwable cause = catchThrowable(() -> validator.validate(event));

        assertThat(cause).isInstanceOf(InvalidLockoutStateRequestException.class);
    }

    private static APIGatewayProxyRequestEvent toEvent(final Map<String, String> parameters) {
        return new APIGatewayProxyRequestEvent().withQueryStringParameters(Collections.unmodifiableMap(parameters));
    }
}
