package uk.co.mruoc.idv.awslambda.verificationcontext;

import lombok.Builder;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.service.IdentityService;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextServiceRequest;
import uk.co.mruoc.idv.core.verificationcontext.service.VerificationContextService;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextRequest;

@Builder
public class Facade {

    private final IdentityService identityService;
    private final VerificationContextService verificationContextService;
    private final VerificationContextRequestConverter requestConverter;

    public VerificationContext create(final VerificationContextRequest request) {
        final Identity identity = identityService.load(request.getProvidedAlias());
        final VerificationContextServiceRequest serviceRequest = requestConverter.toServiceRequest(request, identity);
        return verificationContextService.create(serviceRequest);
    }

}
