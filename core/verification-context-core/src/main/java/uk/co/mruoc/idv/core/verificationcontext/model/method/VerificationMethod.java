package uk.co.mruoc.idv.core.verificationcontext.model.method;


import java.util.Map;

public interface VerificationMethod {

    int DEFAULT_DURATION = 300000;

    String getName();

    int getDuration();

    Map<String, Object> getProperties();

    <T> T get(final String name, final Class<T> type);

    class Names {

        public static final String PUSH_NOTIFICATION = "PUSH_NOTIFICATION";
        public static final String PHYSICAL_PINSENTRY = "PHYSICAL_PINSENTRY";
        public static final String MOBILE_PINSENTRY = "MOBILE_PINSENTRY";
        public static final String CARD_CREDENTIALS = "CARD_CREDENTIALS";
        public static final String ONE_TIME_PASSCODE_SMS = "ONE_TIME_PASSCODE_SMS";

        private Names() {
            // utility class
        }

    }

}
