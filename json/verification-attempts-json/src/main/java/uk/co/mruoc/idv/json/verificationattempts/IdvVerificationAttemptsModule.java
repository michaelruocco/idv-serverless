package uk.co.mruoc.idv.json.verificationattempts;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;

public class IdvVerificationAttemptsModule extends SimpleModule {

    public IdvVerificationAttemptsModule() {
        setUpAttempts();
    }

    private void setUpAttempts() {
        addDeserializer(VerificationAttempts.class, new VerificationAttemptsDeserializer());

        setMixInAnnotation(VerificationAttempt.class, VerificationAttemptMixin.class);
        setMixInAnnotation(VerificationAttempts.class, VerificationAttemptsMixin.class);
    }

}
