package uk.co.mruoc.idv.core.verificationcontext.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

@Builder
@ToString
@Getter
public class CreateVerificationContextRequest {

    private final Channel channel;
    private final Alias alias;
    private final Activity activity;

}
