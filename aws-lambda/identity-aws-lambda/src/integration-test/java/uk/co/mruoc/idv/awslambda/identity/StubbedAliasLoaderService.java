package uk.co.mruoc.idv.awslambda.identity;

import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderRequest;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderService;

public class StubbedAliasLoaderService implements AliasLoaderService {

    @Override
    public Aliases loadAliases(final AliasLoaderRequest request) {
        return Aliases.with(request.getProvidedAlias());
    }

}
