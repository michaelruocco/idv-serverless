package uk.co.mruoc.idv.json.authorizer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.authorizer.model.GenerateTokenRequest;

import java.util.Optional;

@Builder
@RequiredArgsConstructor
@Getter
@NoArgsConstructor(force = true) // required by jackson
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
