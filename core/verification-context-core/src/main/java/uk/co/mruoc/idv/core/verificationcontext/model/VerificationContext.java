package uk.co.mruoc.idv.core.verificationcontext.model;

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
import java.util.Optional;
import java.util.UUID;

@Builder
@ToString
@Getter
public class VerificationContext {

    private final UUID id;
    private final Channel channel;
    private final Alias providedAlias;
    private final Identity identity;
    private final Activity activity;
    private final Instant created;
    private final Instant expiry;
    private final Collection<VerificationMethodSequence> sequences;

    public UUID getIdvId() {
        return identity.getIdvId();
    }

    public Optional<VerificationMethodSequence> getSequence(final String name) {
        return sequences.stream()
                .filter(sequences -> sequences.getName().equals(name))
                .findFirst();
    }

}
