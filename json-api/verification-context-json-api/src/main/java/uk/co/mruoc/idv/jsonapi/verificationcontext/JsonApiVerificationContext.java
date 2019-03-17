package uk.co.mruoc.idv.jsonapi.verificationcontext;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;

import java.time.Instant;
import java.util.Collection;

@Builder
@Getter
@ToString
public class JsonApiVerificationContext {

    private final Channel channel;
    private final Alias providedAlias;
    private final Identity identity;
    private final Activity activity;
    private final Instant created;
    private final Instant expiry;
    private final Collection<VerificationMethodSequence> eligibleMethods;

}
