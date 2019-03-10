package uk.co.mruoc.idv.core.identity.model.alias;

public interface AliasType {

    String name();

    boolean isSensitive();

    boolean isCardNumber();

    class Names {

        public static final String IDV_ID = "IDV_ID";
        public static final String UKC_CARDHOLDER_ID = "UKC_CARDHOLDER_ID";
        public static final String BUK_CUSTOMER_ID = "BUK_CUSTOMER_ID";
        public static final String CREDIT_CARD_NUMBER = "CREDIT_CARD_NUMBER";
        public static final String DEBIT_CARD_NUMBER = "DEBIT_CARD_NUMBER";
        public static final String CARD_NUMBER = "CARD_NUMBER";

        private Names() {
            // utility class
        }

    }

}
