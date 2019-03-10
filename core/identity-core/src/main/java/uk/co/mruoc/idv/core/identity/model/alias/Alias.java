package uk.co.mruoc.idv.core.identity.model.alias;

public interface Alias {

    String getTypeName();

    String getFormat();

    String getValue();

    boolean isSensitive();

    boolean isCardNumber();

    class Formats {

        public static final String CLEAR_TEXT = "CLEAR_TEXT";
        public static final String TOKENIZED = "TOKENIZED";
        public static final String ENCRYPTED = "ENCRYPTED";

        private Formats() {
            // utility class
        }

    }

}
