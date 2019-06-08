package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.AbstractVerificationContextRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;

@Builder
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // required by jackson
public class DefaultVerificationContextRequest extends AbstractVerificationContextRequest {

    private final Channel channel;
    private final Activity activity;
    private final Alias providedAlias;

    @JsonIgnore
    public String getChannelId() {
        return channel.getId();
    }

    @Override
    @JsonIgnore
    public Alias getAlias() {
        return providedAlias;
    }

    @Override
    @JsonIgnore
    public String getActivityType() {
        return activity.getType();
    }

    @Override
    @JsonIgnore
    public String getAliasTypeName() {
        return providedAlias.getTypeName();
    }

}
