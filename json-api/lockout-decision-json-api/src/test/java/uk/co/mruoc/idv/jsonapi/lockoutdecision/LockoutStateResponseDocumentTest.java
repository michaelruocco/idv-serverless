package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.NotLockedTimeBasedIntervalLockoutState;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.json.JsonConverter;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class LockoutStateResponseDocumentTest {

    private static final String JSON = loadContentFromClasspath("/lockout-state-response-document.json");
    private static final LockoutStateResponseDocument DOCUMENT = buildDocument();

    private static final JsonConverter CONVERTER = new JsonApiLockoutDecisionJsonConverterFactory().build();

    @Test
    public void shouldSerializeDocument() throws JSONException {
        final String json = CONVERTER.toJson(DOCUMENT);

        JSONAssert.assertEquals(JSON, json, JSONCompareMode.STRICT);
    }

    @Test
    public void shouldReturnResponse() {
        final LockoutState lockoutState = buildLockoutState();

        final LockoutStateResponseDocument document = new LockoutStateResponseDocument(lockoutState);

        assertThat(document.getLockoutState()).isEqualTo(lockoutState);
    }

    private static LockoutStateResponseDocument buildDocument() {
        final LockoutState lockoutState = buildLockoutState();
        return new LockoutStateResponseDocument(lockoutState);
    }

    private static LockoutState buildLockoutState() {
        final VerificationAttempt attempt = VerificationAttempt.builder()
                .alias(new DefaultAlias(new DefaultAliasType("TYPE"), "FORMAT", "VALUE"))
                .channelId("CHANNEL_ID")
                .activityType("ACTIVITY_TYPE")
                .methodName("METHOD_NAME")
                .timestamp(Instant.parse("2019-03-10T12:53:57.547Z"))
                .successful(false)
                .build();
        final VerificationAttempts attempts = VerificationAttempts.builder()
                .lockoutStateId(UUID.fromString("a20ed37e-0205-4b73-82d0-8435ee74f7a3"))
                .attempts(Collections.singleton(attempt))
                .idvIdAlias(new IdvIdAlias("d98aa22c-a06e-4db5-8dc1-9ea83716ac12"))
                .build();
        return new NotLockedTimeBasedIntervalLockoutState(attempts);
    }

}