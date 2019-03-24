package uk.co.mruoc.idv.plugin.uk.identity.model;

import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;

public class UkcCardholderIdAlias extends DefaultAlias {

    private static final AliasType TYPE = new UkcCardholderIdAliasType();

    public UkcCardholderIdAlias(final String value) {
        super(TYPE, Formats.CLEAR_TEXT, value);
    }

}
