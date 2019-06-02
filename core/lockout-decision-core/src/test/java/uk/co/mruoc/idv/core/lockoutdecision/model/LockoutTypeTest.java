package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LockoutTypeTest {

    @Test
    public void shouldReturnIfTypeIsTimeBased() {
        assertThat(LockoutType.isTimeBased(LockoutType.TIME_BASED_INTERVAL)).isTrue();
        assertThat(LockoutType.isTimeBased(LockoutType.TIME_BASED_RECURRING)).isTrue();
        assertThat(LockoutType.isTimeBased(LockoutType.MAX_ATTEMPTS)).isFalse();
        assertThat(LockoutType.isTimeBased(LockoutType.NON_LOCKING)).isFalse();
    }

}
