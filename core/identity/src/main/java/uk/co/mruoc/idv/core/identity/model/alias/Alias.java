package uk.co.mruoc.idv.core.identity.model.alias;

public interface Alias {

    boolean isCardNumber();

    AliasType getType();

    String getValue();

}
