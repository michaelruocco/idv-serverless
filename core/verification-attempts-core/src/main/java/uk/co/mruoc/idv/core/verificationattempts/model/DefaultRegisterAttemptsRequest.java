package uk.co.mruoc.idv.core.verificationattempts.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // required by jackson
public class DefaultRegisterAttemptsRequest implements RegisterAttemptsRequest {

    private final Collection<RegisterAttemptRequest> attempts;

    public boolean isEmpty() {
        return attempts == null || attempts.isEmpty();
    }

}
