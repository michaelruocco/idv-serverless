package uk.co.mruoc.idv.core.identity.service;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

@Getter
@Builder
@ToString
public class AliasLoaderRequest {

    private final String channelId;
    private final Alias providedAlias;

}
