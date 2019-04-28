package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import uk.co.mruoc.idv.awslambda.verificationcontext.result.PostVerificationResultHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.result.VerificationResultService;

public class UkPostVerificationResultHandler extends PostVerificationResultHandler {

    public UkPostVerificationResultHandler() {
        this(new UkPostVerificationResultServiceFactory().build());
    }

    public UkPostVerificationResultHandler(final VerificationResultService service) {
        super(service);
    }

}
