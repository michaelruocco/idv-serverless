package uk.co.mruoc.idv.core.verificationcontext.model.method;


import java.util.Map;

public interface VerificationMethod {

    int DEFAULT_DURATION = 300000;
    VerificationStatus DEFAULT_STATUS = VerificationStatus.AVAILABLE;

    String getName();

    int getDuration();

    VerificationStatus getStatus();

    Map<String, Object> getProperties();

    <T> T get(final String name, final Class<T> type);

    interface Names {

        String PUSH_NOTIFICATION = "PUSH_NOTIFICATION";
        String PHYSICAL_PINSENTRY = "PHYSICAL_PINSENTRY";
        String MOBILE_PINSENTRY = "MOBILE_PINSENTRY";
        String CARD_CREDENTIALS = "CARD_CREDENTIALS";
        String ONE_TIME_PASSCODE_SMS = "ONE_TIME_PASSCODE_SMS";

    }

}
