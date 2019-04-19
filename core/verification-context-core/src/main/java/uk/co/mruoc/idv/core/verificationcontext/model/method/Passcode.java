package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@Builder
@ToString
public class Passcode {

    private final int length;
    private final int duration;
    private final int maxAttempts;

}
