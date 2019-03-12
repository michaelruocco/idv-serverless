package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerificationMethodPolicy {

    private final String methodName;
    private final int duration;

}
