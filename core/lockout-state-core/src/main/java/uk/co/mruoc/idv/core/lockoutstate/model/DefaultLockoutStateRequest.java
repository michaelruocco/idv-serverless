package uk.co.mruoc.idv.core.lockoutstate.model;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // required by jackson
@ToString
public class DefaultLockoutStateRequest implements LockoutStateRequest {

    private final String channelId;
    private final Alias alias;
    private final String activityType;

    @Override
    public String getChannelId() {
        return channelId;
    }

    @Override
    public Alias getAlias() {
        return alias;
    }

    @Override
    public String getActivityType() {
        return activityType;
    }

    @Override
    public String getAliasTypeName() {
        return alias.getTypeName();
    }

}
