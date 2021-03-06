package uk.co.mruoc.idv.jsonapi.lockoutstate;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.lockoutstate.model.LockedTimeBasedIntervalLockoutState;
import uk.co.mruoc.idv.core.lockoutstate.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutstate.model.MaxAttemptsLockoutState;
import uk.co.mruoc.idv.core.lockoutstate.model.NotLockedTimeBasedIntervalLockoutState;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempt;
import uk.co.mruoc.idv.core.verificationattempts.model.VerificationAttempts;
import uk.co.mruoc.idv.json.JsonConverter;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class LockoutStateResponseDocumentTest {

    private static final JsonConverter CONVERTER = new JsonApiLockoutDecisionJsonConverterFactory().build();

    @Test
    public void shouldSerializeDocumentWithNotLockedState() {
        final String expectedJson = loadContentFromClasspath("/not-locked-lockout-state-response-document.json");
        final LockoutStateResponseDocument document = buildDocument(new NotLockedTimeBasedIntervalLockoutState(buildAttempts()));

        final String json = CONVERTER.toJson(document);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldSerializeDocumentWithTimeBasedLockedState() {
        final Duration duration = Duration.ofMinutes(15);
        final Instant lockedUtil = Instant.parse("2019-03-10T13:08:57.547Z");
        final String expectedJson = loadContentFromClasspath("/locked-time-based-lockout-state-response-document.json");
        final LockoutStateResponseDocument document = buildDocument(new LockedTimeBasedIntervalLockoutState(buildAttempts(), duration, lockedUtil));

        final String json = CONVERTER.toJson(document);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldSerializeDocumentWithMaxAttemptsState() {
        final int attemptsRemaining = 2;
        final String expectedJson = loadContentFromClasspath("/max-attempts-lockout-state-response-document.json");
        final LockoutStateResponseDocument document = buildDocument(new MaxAttemptsLockoutState(buildAttempts(), attemptsRemaining));

        final String json = CONVERTER.toJson(document);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldReturnResponse() {
        final LockoutStateResponse response = mock(LockoutStateResponse.class);

        final LockoutStateResponseDocument document = new LockoutStateResponseDocument(response);

        assertThat(document.getResponse()).isEqualTo(response);
    }

    @Test
    public void shouldReturnId() {
        final UUID id = UUID.randomUUID();
        final LockoutStateResponse response = mock(LockoutStateResponse.class);
        given(response.getId()).willReturn(id);

        final LockoutStateResponseDocument document = new LockoutStateResponseDocument(response);

        assertThat(document.getId()).isEqualTo(id);
    }

    @Test
    public void shouldReturnIdvId() {
        final UUID idvId = UUID.randomUUID();
        final LockoutStateResponse response = mock(LockoutStateResponse.class);
        given(response.getIdvId()).willReturn(idvId);

        final LockoutStateResponseDocument document = new LockoutStateResponseDocument(response);

        assertThat(document.getIdvId()).isEqualTo(idvId);
    }

    @Test
    public void shouldHaveNoArgConstructorForJackson() {
        final LockoutStateResponseDocument document = new LockoutStateResponseDocument();

        assertThat(document).isNotNull();
    }

    private static LockoutStateResponseDocument buildDocument(final LockoutState state) {
        final LockoutStateResponse response = buildLockoutStateResponse(state);
        return new LockoutStateResponseDocument(response);
    }

    private static LockoutStateResponse buildLockoutStateResponse(final LockoutState state) {
        return DefaultLockoutStateResponse.builder()
                .state(state)
                .build();
    }

    private static VerificationAttempts buildAttempts() {
        final VerificationAttempt attempt = VerificationAttempt.builder()
                .contextId(UUID.fromString("17a63c75-b6e4-4302-9050-7288fdaf0b31"))
                .alias(new DefaultAlias(new DefaultAliasType("TYPE"), "FORMAT", "VALUE"))
                .channelId("CHANNEL_ID")
                .verificationId(UUID.fromString("0dcbf980-5a82-4b9f-9cfa-4f72959f392a"))
                .activityType("ACTIVITY_TYPE")
                .methodName("METHOD_NAME")
                .timestamp(Instant.parse("2019-03-10T12:53:57.547Z"))
                .successful(true)
                .build();
        return VerificationAttempts.builder()
                .lockoutStateId(UUID.fromString("a20ed37e-0205-4b73-82d0-8435ee74f7a3"))
                .attempts(Collections.singleton(attempt))
                .idvId(UUID.fromString("d98aa22c-a06e-4db5-8dc1-9ea83716ac12"))
                .build();
    }

}