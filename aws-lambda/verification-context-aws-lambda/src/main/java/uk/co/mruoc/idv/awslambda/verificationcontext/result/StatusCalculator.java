package uk.co.mruoc.idv.awslambda.verificationcontext.result;

import org.apache.http.HttpStatus;
import uk.co.mruoc.idv.core.verificationcontext.model.result.VerificationMethodResults;

public class StatusCalculator {

    public int calculate(final VerificationMethodResults inputResults, final VerificationMethodResults results) {
        final boolean isNew = inputResults.size() == results.size();
        if (isNew) {
            return HttpStatus.SC_CREATED;
        }
        return HttpStatus.SC_OK;
    }

}
