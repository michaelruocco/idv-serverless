package uk.co.mruoc.idv.json.lockoutdecision;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAlias;
import uk.co.mruoc.idv.core.identity.model.alias.DefaultAliasType;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempt;
import uk.co.mruoc.idv.core.lockoutdecision.model.VerificationAttempts;
import uk.co.mruoc.idv.json.JsonConverter;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class VerificationAttemptsDeserializerTest {

    private static final String ATTEMPTS_JSON = loadContentFromClasspath("/verification-attempts.json");
    private static final VerificationAttempts ATTEMPTS = buildAttempts();

    private final JsonConverter converter = new LockoutDecisionJsonConverterFactory().build();

    @Test
    public void shouldDeserialize() {
        final VerificationAttempts attempts = converter.toObject(ATTEMPTS_JSON, VerificationAttempts.class);

        assertThat(attempts.getIdvId()).isEqualTo(ATTEMPTS.getIdvId());
        assertThat(attempts.getLockoutStateId()).isEqualTo(ATTEMPTS.getLockoutStateId());
        assertThat(attempts.getMostRecentTimestamp()).isEqualTo(ATTEMPTS.getMostRecentTimestamp());
        assertThat(attempts).usingElementComparator(new VerificationAttemptComparator())
                .containsExactlyElementsOf(ATTEMPTS);
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
                .idvIdAlias(new IdvIdAlias("1a10908d-e2eb-4680-a271-3d0c4d028a6b"))
                .lockoutStateId(UUID.fromString("21b4d9e0-11c3-4e84-aa87-dc37d7f59e23"))
                .attempts(Collections.singleton(attempt))
                .build();
    }


    private static class VerificationAttemptComparator implements Comparator<VerificationAttempt> {

        private final AliasComparator aliasComparator = new AliasComparator();

        @Override
        public int compare(final VerificationAttempt a1, final VerificationAttempt a2) {
            return a1.getActivityType().compareTo(a2.getActivityType()) +
                    a1.getChannelId().compareTo(a2.getChannelId()) +
                    a1.getTimestamp().compareTo(a2.getTimestamp()) +
                    Boolean.compare(a1.isSuccessful(), a2.isSuccessful()) +
                    a1.getAliasTypeName().compareTo(a2.getAliasTypeName()) +
                    aliasComparator.compare(a1.getAlias(), a2.getAlias());
        }

    }

    private static class AliasComparator implements Comparator<Alias> {

        @Override
        public int compare(final Alias a1, final Alias a2) {
            return a1.getValue().compareTo(a2.getValue()) +
                    a1.getFormat().compareTo(a2.getFormat()) +
                    a1.getTypeName().compareTo(a2.getTypeName());
        }

    }

}
