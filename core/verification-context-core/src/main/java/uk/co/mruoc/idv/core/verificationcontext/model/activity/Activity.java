package uk.co.mruoc.idv.core.verificationcontext.model.activity;

import java.time.Instant;

public interface Activity {

    String getType();

    Instant getTimestamp();

    <T> T get(final String name, final Class<T> type);

    class Types {

        public static final String LOGIN = "LOGIN";
        public static final String ONLINE_PURCHASE = "ONLINE_PURCHASE";

        private Types() {
            // utility class
        }

    }

}
