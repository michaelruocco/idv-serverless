package uk.co.mruoc.idv.core.authorizer.service;

import lombok.Builder;
import uk.co.mruoc.idv.core.authorizer.model.TokenRequest;

import java.util.Optional;
import java.util.UUID;

@Builder
public class DefaultTokenRequest implements TokenRequest {

    private final UUID id;
    private final String subject;
    private final Long timeToLiveInSeconds;

    @Override
    public String getId() {
        return id.toString();
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public Optional<Long> getTimeToLiveInSeconds() {
        return Optional.ofNullable(timeToLiveInSeconds);
    }

}
