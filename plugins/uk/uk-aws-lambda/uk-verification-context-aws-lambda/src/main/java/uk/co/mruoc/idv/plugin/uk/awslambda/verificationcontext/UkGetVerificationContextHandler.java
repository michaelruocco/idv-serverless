package uk.co.mruoc.idv.plugin.uk.awslambda.verificationcontext;

import uk.co.mruoc.idv.awslambda.verificationcontext.GetVerificationContextHandler;
import uk.co.mruoc.idv.core.verificationcontext.service.LoadVerificationContextService;

public class UkGetVerificationContextHandler extends GetVerificationContextHandler {

    public UkGetVerificationContextHandler() {
        this(new UkLoadVerificationContextServiceFactory().build());
    }

    public UkGetVerificationContextHandler(final LoadVerificationContextService service) {
        super(service);
    }

}
