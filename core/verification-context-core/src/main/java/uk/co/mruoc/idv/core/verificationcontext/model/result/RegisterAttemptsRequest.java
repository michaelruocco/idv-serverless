package uk.co.mruoc.idv.core.verificationcontext.model.result;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;

@Builder
@Getter
public class RegisterAttemptsRequest {

    private final VerificationMethodResults newResults;
    private final VerificationContext context;
    private final VerificationMethodResults existingResults;

}
