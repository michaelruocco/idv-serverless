package uk.co.mruoc.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContext;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.Activity;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.method.PushNotificationVerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethod;
import uk.co.mruoc.idv.core.verificationcontext.model.method.VerificationMethodSequence;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class VerificationContextDeserializerTest {

    private static final String JSON = loadContentFromClasspath("/verification-context.json");
    private static final VerificationContext CONTEXT = buildContext();
    private static final ObjectMapper MAPPER = VerificationContextObjectMapperSingleton.get();

    @Test
    public void shouldSerializeContext() throws JsonProcessingException, JSONException {
        final String json = MAPPER.writeValueAsString(CONTEXT);

        JSONAssert.assertEquals(JSON, json, JSONCompareMode.STRICT);
    }

    @Test
    public void shouldDeserializeContext() throws IOException {
        final VerificationContext document = MAPPER.readValue(JSON, VerificationContext.class);

        assertThat(document).isEqualToComparingFieldByFieldRecursively(CONTEXT);
    }

    private static VerificationContext buildContext() {
        final Instant now = Instant.parse("2019-03-10T12:53:57.547Z");
        final Activity activity = new LoginActivity(now);
        final Alias providedAlias = new TokenizedCreditCardNumberAlias("3489347343788005");
        return VerificationContext.builder()
                .id(UUID.fromString("21b4d9e0-11c3-4e84-aa87-dc37d7f59e23"))
                .channel(new DefaultChannel("DEFAULT"))
                .providedAlias(providedAlias)
                .identity(buildIdentity(providedAlias))
                .activity(activity)
                .created(now)
                .expiry(buildExpiry(now))
                .eligibleMethods(buildEligibleMethods())
                .build();
    }

    private static Identity buildIdentity(final Alias providedAlias) {
        final Alias idvId = new IdvIdAlias(UUID.fromString("d98aa22c-a06e-4db5-8dc1-9ea83716ac12"));
        return Identity.withAliases(idvId, providedAlias);
    }

    private static Collection<VerificationMethodSequence> buildEligibleMethods() {
        final int duration = 300000;
        final VerificationMethod method = new PushNotificationVerificationMethod(duration);
        final VerificationMethodSequence sequence = new VerificationMethodSequence(method);
        return Collections.singleton(sequence);
    }

    private static Instant buildExpiry(final Instant now) {
        return now.plus(Duration.ofMinutes(5));
    }

}
