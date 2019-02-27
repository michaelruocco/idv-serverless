package uk.co.mruoc.idv.core.identity.model.alias;

public class UkcCardholderIdAlias extends DefaultAlias {

    private static final AliasType TYPE = new UkcCardholderIdAliasType();

    public UkcCardholderIdAlias(final String value) {
        this(Formats.CLEAR_TEXT, value);
    }

    public UkcCardholderIdAlias(final String format, final String value) {
        super(TYPE, format, value);
    }

}
