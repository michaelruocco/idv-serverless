package uk.co.mruoc.idv.plugin.uk.awslambda.identity;

import uk.co.mruoc.idv.awslambda.identity.GetIdentityHandler;
import uk.co.mruoc.idv.core.identity.service.IdentityService;

public class UkGetIdentityHandler extends GetIdentityHandler {

    public UkGetIdentityHandler() {
        this(new UkIdentityServiceFactory().build());
    }

    public UkGetIdentityHandler(final IdentityService identityService) {
        super(identityService);
    }

}
