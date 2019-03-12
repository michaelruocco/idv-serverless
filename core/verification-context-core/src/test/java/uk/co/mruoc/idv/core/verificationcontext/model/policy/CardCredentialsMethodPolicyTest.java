package uk.co.mruoc.idv.core.verificationcontext.model.policy;

import org.junit.Test;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;

import static org.assertj.core.api.Assertions.assertThat;

public class CardCredentialsMethodPolicyTest {

    private static final int DURATION = 300000;

    private final VerificationMethodPolicy policy = new CardCredentialsMethodPolicy(DURATION);

    @Test
    public void shouldReturnMethodName() {
        assertThat(policy.getMethodName()).isEqualTo(VerificationMethod.Names.CARD_CREDENTIALS);
    }

    @Test
    public void shouldReturnDuration() {
        assertThat(policy.getDuration()).isEqualTo(DURATION);
    }

}
