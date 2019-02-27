package uk.co.mruoc.idv.core.identity.model.alias;

public class BukCustomerIdAlias extends DefaultAlias {

    private static final AliasType TYPE = new BukCustomerIdAliasType();

    public BukCustomerIdAlias(final String value) {
        this(Formats.CLEAR_TEXT, value);
    }

    public BukCustomerIdAlias(final String format, final String value) {
        super(TYPE, format, value);
    }

}
