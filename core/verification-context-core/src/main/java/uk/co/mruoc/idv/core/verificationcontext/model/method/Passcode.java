package uk.co.mruoc.idv.core.verificationcontext.model.method;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class Passcode {

    private final int length;
    private final int duration;
    private final int attempts;

}
