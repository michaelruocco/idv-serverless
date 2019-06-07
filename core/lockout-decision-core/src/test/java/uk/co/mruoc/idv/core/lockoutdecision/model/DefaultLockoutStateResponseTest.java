package uk.co.mruoc.idv.core.lockoutdecision.model;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class DefaultLockoutStateRequestTest {

    @Test
    public void shouldReturnChannelId() {
        final String channelId = "channelId";

        final LockoutStateRequest request = DefaultLockoutStateRequest.builder()
                .channelId(channelId)
                .build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    public void shouldReturnAlias() {
        final Alias alias = mock(Alias.class);

        final LockoutStateRequest request = DefaultLockoutStateRequest.builder()
                .alias(alias)
                .build();

        assertThat(request.getAlias()).isEqualTo(alias);
        assertThat(request.getAliasTypeName()).isEqualTo(alias.getTypeName());
    }

    @Test
    public void shouldReturnActivityType() {
        final String activityType = "activityType";

        final LockoutStateRequest request = DefaultLockoutStateRequest.builder()
                .activityType(activityType)
                .build();

        assertThat(request.getActivityType()).isEqualTo(activityType);
    }

    @Test
    public void shouldReturnEmptyOptionalIfMethodNameNotSet() {
        final LockoutStateRequest request = DefaultLockoutStateRequest.builder()
                .build();

        assertThat(request.getMethodName()).isEmpty();
    }

    @Test
    public void shouldReturnMethodName() {
        final String methodName = "methodName";

        final LockoutStateRequest request = DefaultLockoutStateRequest.builder()
                .methodName(methodName)
                .build();

        assertThat(request.getMethodName()).contains(methodName);
    }

}