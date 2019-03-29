package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import uk.co.mruoc.idv.awslambda.verificationcontext.PostVerificationContextHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.CreateVerificationContextService;

public class UkPostVerificationContextHandler extends PostVerificationContextHandler {

    public UkPostVerificationContextHandler() {
        this(new UkPostVerificationContextServiceFactory().build());
    }

    public UkPostVerificationContextHandler(final CreateVerificationContextService service) {
        super(service);
    }

}
