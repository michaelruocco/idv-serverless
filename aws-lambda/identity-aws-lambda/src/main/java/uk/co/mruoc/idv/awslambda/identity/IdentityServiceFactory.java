package uk.co.mruoc.idv.awslambda.identity;

import uk.co.mruoc.idv.core.identity.service.IdentityService;

public interface IdentityServiceFactory {

    IdentityService build();

}
