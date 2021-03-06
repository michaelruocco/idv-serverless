package uk.co.mruoc.idv.jsonapi.verificationcontext;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.channel.model.DefaultChannel;
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
import static uk.co.mruoc.file.ContentLoader.*;

public class VerificationContextResponseDocumentTest {

    private static final String JSON = loadContentFromClasspath("/verification-context-response-document.json");
    private static final VerificationContextResponseDocument DOCUMENT = buildDocument();

    private static final JsonConverter CONVERTER = new JsonApiVerificationContextJsonConverterFactory().build();

    @Test
    public void shouldSerializeDocument() {
        final String json = CONVERTER.toJson(DOCUMENT);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeDocument() {
        final VerificationContextResponseDocument document = CONVERTER.toObject(JSON, VerificationContextResponseDocument.class);

        assertThat(document).isEqualToComparingFieldByFieldRecursively(DOCUMENT);
    }

    @Test
    public void canBeCreatedUsingIdFromContext() {
        final VerificationContext request = buildContext()
                .id(UUID.fromString("21b4d9e0-11c3-4e84-aa87-dc37d7f59e23"))
                .build();

        final VerificationContextResponseDocument document = new VerificationContextResponseDocument(request);

        assertThat(document.getContext()).isEqualTo(request);
    }

    @Test
    public void shouldReturnId() {
        final UUID id = UUID.fromString("21b4d9e0-11c3-4e84-aa87-dc37d7f59e23");
        final VerificationContext request = buildContext()
                .id(id)
                .build();

        final VerificationContextResponseDocument document = new VerificationContextResponseDocument(request);

        assertThat(document.getId()).isEqualTo(id);
    }

    @Test
    public void shouldReturnIdvId() {
        final VerificationContextResponseDocument document = CONVERTER.toObject(JSON, VerificationContextResponseDocument.class);

        assertThat(document.getIdvId()).isEqualTo(UUID.fromString("d98aa22c-a06e-4db5-8dc1-9ea83716ac12"));
    }

    @Test
    public void shouldReturnCreated() {
        final VerificationContextResponseDocument document = CONVERTER.toObject(JSON, VerificationContextResponseDocument.class);

        assertThat(document.getCreated()).isEqualTo("2019-03-10T12:53:57.547Z");
    }

    @Test
    public void shouldReturnExpiry() {
        final VerificationContextResponseDocument document = CONVERTER.toObject(JSON, VerificationContextResponseDocument.class);

        assertThat(document.getExpiry()).isEqualTo("2019-03-10T12:58:57.547Z");
    }

    private static VerificationContextResponseDocument buildDocument() {
        final VerificationContext request = buildContext().build();
        final UUID id = UUID.fromString("21b4d9e0-11c3-4e84-aa87-dc37d7f59e23");
        return new VerificationContextResponseDocument(id, request);
    }

    private static VerificationContextBuilder buildContext() {
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