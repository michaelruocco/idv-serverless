package uk.co.mruoc.idv.core.lockoutdecision.model;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import java.util.Optional;

public interface LoadLockoutStateRequest {
    
    String getChannelId();
    
    Alias getAlias();

    String getActivityType();

    String getAliasTypeName();

    Optional<String> getMethodName();
}
