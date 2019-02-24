package uk.co.mruoc.idv.core.identity.service;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;

@Getter
@Builder
public class AliasLoaderRequest {

    private final String channelId;
    private final Aliases aliases;

}
