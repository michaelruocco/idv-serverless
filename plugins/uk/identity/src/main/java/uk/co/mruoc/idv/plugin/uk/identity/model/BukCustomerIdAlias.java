package uk.co.mruoc.idv.plugin.uk.identity.model;

import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;

public class BukCustomerIdAlias extends DefaultAlias {

    private static final AliasType TYPE = new BukCustomerIdAliasType();

    public BukCustomerIdAlias(final String value) {
        super(TYPE, Formats.CLEAR_TEXT, value);
    }

}
