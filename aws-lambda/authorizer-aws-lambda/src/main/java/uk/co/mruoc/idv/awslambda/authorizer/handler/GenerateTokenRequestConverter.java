package uk.co.mruoc.idv.awslambda.authorizer.handler;

import lombok.Builder;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;
import uk.co.mruoc.idv.core.authorizer.model.TokenRequest;
import uk.co.mruoc.idv.core.authorizer.service.DefaultTokenRequest;
import uk.co.mruoc.idv.core.authorizer.service.DefaultTokenRequest.DefaultTokenRequestBuilder;
import uk.co.mruoc.idv.core.service.UuidGenerator;

@Builder
public class GenerateTokenRequestConverter {

    private UuidGenerator uuidGenerator;

    public TokenRequest toTokenRequest(final GenerateTokenRequest request) {
        if (request.getTimeToLiveInSeconds().isPresent()) {
            return toExpiringTokenRequest(request);
        }
        return toNonExpiringTokenRequest(request);
    }

    private TokenRequest toExpiringTokenRequest(final GenerateTokenRequest request) {
        return toBuilder(request)
                .timeToLiveInSeconds(request.getTimeToLiveInSeconds().get())
                .build();
    }

    private TokenRequest toNonExpiringTokenRequest(final GenerateTokenRequest request) {
        return toBuilder(request)
                .build();
    }

    private DefaultTokenRequestBuilder toBuilder(final GenerateTokenRequest request) {
        return DefaultTokenRequest.builder()
                .id(uuidGenerator.randomUuid())
                .subject(request.getSubject());
    }

}
