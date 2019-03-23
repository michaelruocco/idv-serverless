package uk.co.mruoc.idv.core.verificationcontext.service;

import uk.co.mruoc.idv.core.identity.service.UpsertIdentityRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;

public class VerificationContextRequestConverter {

    public UpsertIdentityRequest toUpsertIdentityRequest(final VerificationContextRequest contextRequest) {
        return UpsertIdentityRequest.builder()
                .providedAlias(contextRequest.getProvidedAlias())
                .channelId(contextRequest.getChannelId())
                .build();
    }

}
