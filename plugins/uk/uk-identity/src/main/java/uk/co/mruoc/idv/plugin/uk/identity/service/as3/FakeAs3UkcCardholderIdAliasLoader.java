package uk.co.mruoc.idv.plugin.uk.identity.service.as3;

import uk.co.mruoc.idv.core.identity.service.DefaultAliasLoader;
import uk.co.mruoc.idv.plugin.uk.identity.model.UkAliasType;

import java.util.Collection;

import static java.util.Collections.singleton;

public class FakeAs3UkcCardholderIdAliasLoader extends DefaultAliasLoader {

    private static final Collection<String> SUPPORTED_CHANNEL_IDS = singleton("AS3");
    private static final Collection<String> SUPPORTED_ALIAS_TYPES = singleton(UkAliasType.Names.UKC_CARDHOLDER_ID);

    public FakeAs3UkcCardholderIdAliasLoader() {
        super(SUPPORTED_CHANNEL_IDS, SUPPORTED_ALIAS_TYPES, new FakeUkcCardholderIdAliasHandler());
    }

}
