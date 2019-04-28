package uk.co.mruoc.idv.awslambda.verificationcontext.result;

import org.apache.http.HttpStatus;
import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResult;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class StatusCalculatorTest {

    private final StatusCalculator calculator = new StatusCalculator();

    @Test
    public void shouldReturnCreatedStatusIfInputResultsIsSameSizeAsResults() {
        final VerificationMethodResults inputResults = VerificationMethodResults.builder()
                .results(Collections.emptyList())
                .build();
        final VerificationMethodResults results = VerificationMethodResults.builder()
                .results(Collections.emptyList())
                .build();

        final int status = calculator.calculate(inputResults, results);

        assertThat(status).isEqualTo(HttpStatus.SC_CREATED);
    }

    @Test
    public void shouldReturnOkStatusIfInputResultsHasDifferentSizeToResults() {
        final VerificationMethodResult result = mock(VerificationMethodResult.class);
        final VerificationMethodResults inputResults = VerificationMethodResults.builder()
                .results(Collections.singleton(result))
                .build();
        final VerificationMethodResults results = VerificationMethodResults.builder()
                .results(Collections.emptyList())
                .build();

        final int status = calculator.calculate(inputResults, results);

        assertThat(status).isEqualTo(HttpStatus.SC_OK);
    }

}
