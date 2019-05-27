package uk.co.mruoc.idv.json.lockoutdecision;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.json.JsonConverter;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class LockoutDecisionJsonConverterFactoryTest {

    private static final String PATH_TO_ATTEMPTS = "/verification-attempts.json";

    private static final JsonConverter CONVERTER = new LockoutDecisionJsonConverterFactory().build();

    @Test
    public void shouldConvertVerificationAttemptsToJson() throws JSONException {
        final String expectedJson = loadContentFromClasspath(PATH_TO_ATTEMPTS);

        final String json = CONVERTER.toJson(buildAttempts());

        JSONAssert.assertEquals(expectedJson, json, JSONCompareMode.STRICT);
    }

    private static VerificationAttempts buildAttempts() {
        final VerificationAttempt attempt = VerificationAttempt.builder()
                .channelId("CHANNEL_ID")
                .activityType("ACTIVITY_TYPE")
                .methodName("METHOD_NAME")
                .alias(new DefaultAlias(new DefaultAliasType("TYPE"), "FORMAT", "VALUE"))
                .timestamp(Instant.parse("2019-03-10T12:53:57.547Z"))
                .successful(true)
                .build();
        return VerificationAttempts.builder()
                .idvId(UUID.fromString("1a10908d-e2eb-4680-a271-3d0c4d028a6b"))
                .lockoutStateId(UUID.fromString("21b4d9e0-11c3-4e84-aa87-dc37d7f59e23"))
                .attempts(Collections.singleton(attempt))
                .build();
    }

}
