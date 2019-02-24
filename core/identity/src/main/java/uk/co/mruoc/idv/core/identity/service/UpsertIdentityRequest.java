package uk.co.mruoc.idv.core.identity.service;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

@Getter
@Builder
@ToString
public class UpsertIdentityRequest {

    private final String channelId;
    private final Alias alias;

}
