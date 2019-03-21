package uk.co.mruoc.idv.core.verificationcontext.model.activity;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends DefaultActivity {

    public LoginActivity(final Instant timestamp) {
        this(timestamp, new HashMap<>());
    }

    public LoginActivity(final Instant timestamp, final Map<String, Object> properties) {
        super(Types.LOGIN, timestamp, properties);
    }

}
