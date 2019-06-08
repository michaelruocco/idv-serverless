package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockedTimeBasedIntervalLockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.MaxAttemptsLockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.NotLockedTimeBasedIntervalLockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.json.JsonConverter;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class LockoutStateResponseDocumentTest {

    private static final JsonConverter CONVERTER = new JsonApiLockoutDecisionJsonConverterFactory().build();

    @Test
    public void shouldSerializeDocumentWithNotLockedState() throws JSONException {
        final String expectedJson = loadContentFromClasspath("/not-locked-lockout-state-response-document.json");
        final LockoutStateResponseDocument document = buildDocument(new NotLockedTimeBasedIntervalLockoutState(buildAttempts()));

        final String json = CONVERTER.toJson(document);

        JSONAssert.assertEquals(expectedJson, json, JSONCompareMode.STRICT);
    }

    @Test
    public void shouldSerializeDocumentWithTimeBasedLockedState() throws JSONException {
        final Duration duration = Duration.ofMinutes(15);
        final Instant lockedUtil = Instant.parse("2019-03-10T13:08:57.547Z");
        final String expectedJson = loadContentFromClasspath("/locked-time-based-lockout-state-response-document.json");
        final LockoutStateResponseDocument document = buildDocument(new LockedTimeBasedIntervalLockoutState(buildAttempts(), duration, lockedUtil));

        final String json = CONVERTER.toJson(document);

        JSONAssert.assertEquals(expectedJson, json, JSONCompareMode.STRICT);
    }

    @Test
    public void shouldSerializeDocumentWithMaxAttemptsState() throws JSONException {
        final int attemptsRemaining = 2;
        final String expectedJson = loadContentFromClasspath("/max-attempts-lockout-state-response-document.json");
        final LockoutStateResponseDocument document = buildDocument(new MaxAttemptsLockoutState(buildAttempts(), attemptsRemaining));

        final String json = CONVERTER.toJson(document);

        JSONAssert.assertEquals(expectedJson, json, JSONCompareMode.STRICT);
    }

    @Test
    public void shouldReturnResponse() {
        final LockoutState state = new NotLockedTimeBasedIntervalLockoutState(buildAttempts());
        final LockoutStateResponse response = buildLockoutStateResponse(state);

        final LockoutStateResponseDocument document = new LockoutStateResponseDocument(response);

        assertThat(document.getResponse()).isEqualTo(response);
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
                .alias(new DefaultAlias(new DefaultAliasType("TYPE"), "FORMAT", "VALUE"))
                .channelId("CHANNEL_ID")
                .activityType("ACTIVITY_TYPE")
                .methodName("METHOD_NAME")
                .timestamp(Instant.parse("2019-03-10T12:53:57.547Z"))
                .successful(false)
                .build();
        return VerificationAttempts.builder()
                .lockoutStateId(UUID.fromString("a20ed37e-0205-4b73-82d0-8435ee74f7a3"))
                .attempts(Collections.singleton(attempt))
                .idvId(UUID.fromString("d98aa22c-a06e-4db5-8dc1-9ea83716ac12"))
                .build();
    }

}