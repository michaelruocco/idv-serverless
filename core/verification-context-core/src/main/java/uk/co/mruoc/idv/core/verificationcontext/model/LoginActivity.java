package uk.co.mruoc.idv.core.verificationcontext.model;

import java.time.Instant;

public class LoginActivity extends DefaultActivity {

    public LoginActivity(final Instant timestamp) {
        super(Types.LOGIN, timestamp);
    }

}
