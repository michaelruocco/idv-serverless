package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import uk.co.mruoc.idv.awslambda.verificationcontext.PostVerificationContextHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextService;

public class UkPostVerificationContextHandler extends PostVerificationContextHandler {

    public UkPostVerificationContextHandler() {
        this(new UkVerificationContextServiceFactory().build());
    }

    public UkPostVerificationContextHandler(final VerificationContextService service) {
        super(service);
    }

}
