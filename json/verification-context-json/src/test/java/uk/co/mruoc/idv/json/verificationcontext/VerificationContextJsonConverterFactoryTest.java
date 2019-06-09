package uk.co.mruoc.idv.json.verificationcontext;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext.VerificationContextBuilder;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;
import uk.co.mruoc.idv.json.JsonConverter;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class VerificationContextJsonConverterFactoryTest {

    private static final String PATH_TO_CONTEXT_WITH_ID = "/verification-context-with-id.json";
    private static final String PATH_TO_CONTEXT_WITHOUT_ID = "/verification-context-without-id.json";

    private static final JsonConverter CONVERTER = new VerificationContextJsonConverterFactory().build();

    @Test
    public void shouldConvertVerificationContextWithIdToJson() {
        final String expectedJson = loadContentFromClasspath(PATH_TO_CONTEXT_WITH_ID);

        final String json = CONVERTER.toJson(buildContextWithId());

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldConvertJsonToVerificationContextWithId() {
        final String json = loadContentFromClasspath(PATH_TO_CONTEXT_WITH_ID);

        final VerificationContext document = CONVERTER.toObject(json, VerificationContext.class);

        assertThat(document).isEqualToComparingFieldByFieldRecursively(buildContextWithId());
    }

    @Test
    public void shouldConvertVerificationContextWithoutIdToJson() {
        final String expectedJson = loadContentFromClasspath(PATH_TO_CONTEXT_WITHOUT_ID);

        final String json = CONVERTER.toJson(buildContextWithoutId());

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    public void shouldConvertJsonToVerificationContextWithoutId() {
        final String json = loadContentFromClasspath(PATH_TO_CONTEXT_WITHOUT_ID);

        final VerificationContext document = CONVERTER.toObject(json, VerificationContext.class);

        assertThat(document).isEqualToComparingFieldByFieldRecursively(buildContextWithoutId());
    }

    private static VerificationContext buildContextWithId() {
        return buildContextBuilder()
                .id(UUID.fromString("21b4d9e0-11c3-4e84-aa87-dc37d7f59e23"))
                .build();
    }

    private static VerificationContext buildContextWithoutId() {
        return buildContextBuilder().build();
    }

    private static VerificationContextBuilder buildContextBuilder() {
        final Instant now = Instant.parse("2019-03-10T12:53:57.547Z");
        final Activity activity = new LoginActivity(now);
        final Alias providedAlias = new TokenizedCreditCardNumberAlias("3489347343788005");
        return VerificationContext.builder()
                .channel(new DefaultChannel("DEFAULT"))
                .providedAlias(providedAlias)
                .identity(buildIdentity(providedAlias))
                .activity(activity)
                .created(now)
                .expiry(buildExpiry(now))
                .sequences(buildVerificationMethodSequences());
    }

    private static Identity buildIdentity(final Alias providedAlias) {
        final Alias idvId = new IdvIdAlias(UUID.fromString("d98aa22c-a06e-4db5-8dc1-9ea83716ac12"));
        return Identity.withAliases(idvId, providedAlias);
    }

    private static Collection<VerificationMethodSequence> buildVerificationMethodSequences() {
        final int duration = 300000;
        final VerificationMethod method = new PushNotificationVerificationMethod(duration);
        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);
        return Collections.singleton(sequence);
    }

    private static Instant buildExpiry(final Instant now) {
        return now.plus(Duration.ofMinutes(5));
    }

}
