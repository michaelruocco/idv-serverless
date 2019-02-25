package uk.co.mruoc.idv.core.identity.model.alias;

public interface Alias {

    AliasType getType();

    String getValue();

    boolean isCardNumber();

    boolean isSensitive();

}
