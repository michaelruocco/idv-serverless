package uk.co.mruoc.idv.core.lockoutdecision.model;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import java.util.Optional;

@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // required by jackson
public class DefaultLockoutStateRequest implements LockoutStateRequest {

    private final String channelId;
    private final Alias alias;
    private final String activityType;
    private final String methodName;

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

    @Override
    public Optional<String> getMethodName() {
        return Optional.ofNullable(methodName);
    }

}
