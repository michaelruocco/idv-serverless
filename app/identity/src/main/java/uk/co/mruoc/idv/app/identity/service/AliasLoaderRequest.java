package uk.co.mruoc.idv.app.identity.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.app.identity.model.alias.Aliases;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class AliasLoaderRequest {

    private final String channelId;
    private final Aliases aliases;

}
