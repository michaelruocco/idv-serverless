package uk.co.mruoc.idv.core.lockoutdecision.model;

import uk.co.mruoc.idv.core.identity.model.alias.Alias;

public interface LockoutStateRequest {
    
    String getChannelId();
    
    Alias getAlias();

    String getActivityType();

    String getAliasTypeName();

}
