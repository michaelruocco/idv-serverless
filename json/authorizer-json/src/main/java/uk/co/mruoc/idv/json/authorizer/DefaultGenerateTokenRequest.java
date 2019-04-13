package uk.co.mruoc.idv.json.authorizer;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;

import java.util.Optional;

@Builder
@Getter
public class DefaultGenerateTokenRequest implements GenerateTokenRequest {

    private final String subject;
    private final Long validForSeconds;

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public Optional<Long> getTimeToLiveInSeconds() {
        return Optional.ofNullable(validForSeconds);
    }

}
