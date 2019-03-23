package uk.co.mruoc.idv.core.identity.model.alias;

public interface Alias {

    String getTypeName();

    String getFormat();

    String getValue();

    boolean isSensitive();

    boolean isCardNumber();

    interface Formats {

        String CLEAR_TEXT = "CLEAR_TEXT";
        String TOKENIZED = "TOKENIZED";
        String ENCRYPTED = "ENCRYPTED";

    }

}
