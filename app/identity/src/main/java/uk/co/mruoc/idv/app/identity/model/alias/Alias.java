package uk.co.mruoc.idv.app.identity.model.alias;

public interface Alias {

    boolean isCardNumber();

    AliasType getType();

    String getValue();

}
