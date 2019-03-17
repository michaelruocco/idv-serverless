package uk.co.mruoc.idv.jsonapi.verificationcontext;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;

@Builder
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // required by jackson
public class DefaultVerificationContextRequest implements VerificationContextRequest {

    private final Channel channel;
    private final Activity activity;
    private final Alias providedAlias;

}
