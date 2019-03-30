package uk.co.mruoc.idv.awslambda.authorizer;

import org.junit.Test;
import uk.co.mruoc.idv.awslambda.authorizer.model.ApiGatewayMethodArn;
import uk.co.mruoc.idv.awslambda.authorizer.model.ApiGatewayMethodArnParser;
import uk.co.mruoc.idv.awslambda.authorizer.model.ApiGatewayMethodArnParser.InvalidApiGatewayArnException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ApiGatewayMethodArnParserTest {

    private static final String RAW_ARN = "arn:aws:execute-api:eu-west-1:327122349051:8tu67utdf7/*/GET/verificationContexts/*";

    private final ApiGatewayMethodArnParser parser = new ApiGatewayMethodArnParser();

    @Test
    public void shouldThrowExceptionIfRawArnIsNotValidFormat() {
        final String invalidArn = "invalidArn";

        final Throwable cause = catchThrowable(() -> parser.parse(invalidArn));

        assertThat(cause).isInstanceOf(InvalidApiGatewayArnException.class)
                .hasCauseInstanceOf(ArrayIndexOutOfBoundsException.class)
                .hasMessage(invalidArn);
    }

    @Test
    public void shouldHandleArnWithoutApiElements() {
        final String rawArnWithoutApi = "arn:aws:execute-api:eu-west-1:327122349051:8tu67utdf7";

        final Throwable cause = catchThrowable(() -> parser.parse(rawArnWithoutApi));

        assertThat(cause).isInstanceOf(InvalidApiGatewayArnException.class)
                .hasCauseInstanceOf(ArrayIndexOutOfBoundsException.class)
                .hasMessage(rawArnWithoutApi);
    }

    @Test
    public void shouldReturnArnValue() {
        final ApiGatewayMethodArn arn = parser.parse(RAW_ARN);

        assertThat(arn.getRawValue()).isEqualTo(RAW_ARN);
    }

}
