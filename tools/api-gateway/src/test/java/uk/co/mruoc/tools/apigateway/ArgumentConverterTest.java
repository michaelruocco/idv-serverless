package uk.co.mruoc.tools.apigateway;

import org.apache.commons.cli.ParseException;
import org.junit.Test;
import uk.co.mruoc.tools.apigateway.ArgumentConverter.InvalidArgumentsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ArgumentConverterTest {

    private final ArgumentConverter converter = new ArgumentConverter();

    @Test
    public void shouldThrowExceptionIfMandatoryNameAndStageArgumentsAreMissing() {
        final String[] args = new String[0];

        final Throwable cause = catchThrowable(() -> converter.toFindApiRequest(args));

        assertThat(cause).isInstanceOf(InvalidArgumentsException.class)
                .hasCauseInstanceOf(ParseException.class)
                .hasMessage("org.apache.commons.cli.MissingOptionException: Missing required options: n, s");
    }

    @Test
    public void shouldThrowExceptionIfMandatoryStageArgumentIsMissing() {
        final String[] args = new String[] { "-n", "name" };

        final Throwable cause = catchThrowable(() -> converter.toFindApiRequest(args));

        assertThat(cause).isInstanceOf(InvalidArgumentsException.class)
                .hasCauseInstanceOf(ParseException.class)
                .hasMessage("org.apache.commons.cli.MissingOptionException: Missing required option: s");
    }

    @Test
    public void shouldThrowExceptionIfMandatoryNameArgumentIsMissing() {
        final String[] args = new String[] { "-s", "stage" };

        final Throwable cause = catchThrowable(() -> converter.toFindApiRequest(args));

        assertThat(cause).isInstanceOf(InvalidArgumentsException.class)
                .hasCauseInstanceOf(ParseException.class)
                .hasMessage("org.apache.commons.cli.MissingOptionException: Missing required option: n");
    }

    @Test
    public void shouldDefaultRegionToEuWestOneIfNotProvided() {
        final String[] args = new String[] { "-n", "name", "-s", "stage", };

        final FindApiRequest request = converter.toFindApiRequest(args);

        assertThat(request.getRegion()).isEqualTo("eu-west-1");
    }

    @Test
    public void shouldPopulateName() {
        final String[] args = new String[] { "-n", "name", "-s", "stage", };

        final FindApiRequest request = converter.toFindApiRequest(args);
        assertThat(request.getName()).isEqualTo("name");
    }

    @Test
    public void shouldPopulateStage() {
        final String[] args = new String[] { "-n", "name", "-s", "stage", };

        final FindApiRequest request = converter.toFindApiRequest(args);

        assertThat(request.getStage()).isEqualTo("stage");
    }

    @Test
    public void shouldPopulateRegion() {
        final String[] args = new String[] { "-n", "name", "-s", "stage", "-r", "region" };

        final FindApiRequest request = converter.toFindApiRequest(args);

        assertThat(request.getRegion()).isEqualTo("region");
    }

}
