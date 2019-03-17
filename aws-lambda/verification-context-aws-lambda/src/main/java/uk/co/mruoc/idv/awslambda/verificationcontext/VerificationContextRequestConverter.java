package uk.co.mruoc.idv.awslambda.verificationcontext;

import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextServiceRequest;
import uk.co.mruoc.idv.jsonapi.verificationcontext.VerificationContextRequest;

public class VerificationContextRequestConverter {

    public VerificationContextServiceRequest toServiceRequest(final VerificationContextRequest request, final Identity identity) {
        return VerificationContextServiceRequest.builder()
                .channel(request.getChannel())
                .activity(request.getActivity())
                .providedAlias(request.getProvidedAlias())
                .identity(identity)
                .build();
    }

}
