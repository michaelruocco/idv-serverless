package uk.co.mruoc.idv.core.verificationattempts.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Collections;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // required by jackson
public class DefaultRegisterAttemptsRequest implements RegisterAttemptsRequest {

    private final Collection<RegisterAttemptRequest> attempts;

    public Collection<RegisterAttemptRequest> getAttempts() {
        if (attempts == null) {
            return Collections.emptyList();
        }
        return attempts;
    }

}
