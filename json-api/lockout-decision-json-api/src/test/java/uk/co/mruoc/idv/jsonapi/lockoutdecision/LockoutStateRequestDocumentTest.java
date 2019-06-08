package uk.co.mruoc.idv.jsonapi.lockoutdecision;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.lockoutdecision.model.DefaultLockoutStateRequest;
import uk.co.mruoc.idv.core.lockoutdecision.model.LockoutStateRequest;
import uk.co.mruoc.idv.json.JsonConverter;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class LockoutStateRequestDocumentTest {

    private static final String JSON = loadContentFromClasspath("/lockout-state-request-document.json");
    private static final LockoutStateRequestDocument DOCUMENT = buildDocument();

    private static final JsonConverter CONVERTER = new JsonApiLockoutDecisionJsonConverterFactory().build();

    @Test
    public void shouldSerializeDocument() {
        final String json = CONVERTER.toJson(DOCUMENT);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeDocument() {
        final LockoutStateRequestDocument document = CONVERTER.toObject(JSON, LockoutStateRequestDocument.class);

        assertThat(document).isEqualToComparingFieldByFieldRecursively(DOCUMENT);
    }

    @Test
    public void shouldReturnRequest() {
        final LockoutStateRequest request = buildRequest();

        final LockoutStateRequestDocument document = new LockoutStateRequestDocument(request);

        assertThat(document.getRequest()).isEqualTo(request);
    }

    private static LockoutStateRequestDocument buildDocument() {
        final LockoutStateRequest request = buildRequest();
        return new LockoutStateRequestDocument(request);
    }

    private static LockoutStateRequest buildRequest() {
        final Alias alias = new TokenizedCreditCardNumberAlias("3489347343788005");
        return DefaultLockoutStateRequest.builder()
                .channelId("DEFAULT")
                .activityType("LOGIN")
                .alias(alias)
                .build();
    }

}