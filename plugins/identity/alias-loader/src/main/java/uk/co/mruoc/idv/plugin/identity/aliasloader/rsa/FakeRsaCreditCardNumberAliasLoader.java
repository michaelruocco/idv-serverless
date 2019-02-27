package uk.co.mruoc.idv.plugin.identity.aliasloader.rsa;

import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.CreditCardNumberAliasType;
import uk.co.mruoc.idv.core.identity.service.DefaultAliasLoader;

import java.util.Collection;

import static java.util.Collections.singleton;

public class FakeRsaCreditCardNumberAliasLoader extends DefaultAliasLoader {

    private static final Collection<String> SUPPORTED_CHANNEL_IDS = singleton("RSA");
    private static final Collection<String> SUPPORTED_ALIAS_TYPES = singleton(CreditCardNumberAliasType.NAME);

    public FakeRsaCreditCardNumberAliasLoader() {
        super(SUPPORTED_CHANNEL_IDS, SUPPORTED_ALIAS_TYPES, new FakeCreditCardNumberAliasLoader());
    }

}
