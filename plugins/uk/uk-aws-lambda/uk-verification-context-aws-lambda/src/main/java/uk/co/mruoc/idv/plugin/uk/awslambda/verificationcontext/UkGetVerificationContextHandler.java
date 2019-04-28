package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import uk.co.mruoc.idv.awslambda.verificationcontext.GetVerificationContextHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.GetVerificationContextService;

public class UkGetVerificationContextHandler extends GetVerificationContextHandler {

    public UkGetVerificationContextHandler() {
        this(new UkGetVerificationContextServiceFactory().build());
    }

    public UkGetVerificationContextHandler(final GetVerificationContextService service) {
        super(service);
    }

}
