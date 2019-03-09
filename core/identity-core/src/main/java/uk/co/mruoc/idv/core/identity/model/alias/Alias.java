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

    class Types {

        public static final String IDV_ID = "IDV_ID";
        public static final String UKC_CARDHOLDER_ID = "UKC_CARDHOLDER_ID";
        public static final String BUK_CUSTOMER_ID = "BUK_CUSTOMER_ID";
        public static final String CREDIT_CARD_NUMBER = "CREDIT_CARD_NUMBER";
        public static final String DEBIT_CARD_NUMBER = "DEBIT_CARD_NUMBER";
        public static final String CARD_NUMBER = "CARD_NUMBER";

        private Types() {
            // utility class
        }

    }

}
