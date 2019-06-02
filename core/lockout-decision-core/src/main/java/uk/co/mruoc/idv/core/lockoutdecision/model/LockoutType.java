package uk.co.mruoc.idv.core.lockoutdecision.model;

public interface LockoutType {

    String TIME_BASED_INTERVAL = "TIME_BASED_INTERVAL";
    String TIME_BASED_RECURRING = "TIME_BASED_RECURRING";
    String MAX_ATTEMPTS = "MAX_ATTEMPTS";
    String NON_LOCKING = "NON_LOCKING";

    static boolean isTimeBased(final String lockoutType) {
        return lockoutType.contains("TIME_BASED");
    }

}
