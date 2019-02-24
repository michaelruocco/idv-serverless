package uk.co.mruoc.idv.core.identity.model.alias;

public interface Alias {

    boolean isCardNumber();

    boolean isSensitive();

    boolean hasFormat(final AliasFormat format);

    AliasType getType();

    String getValue();

}
