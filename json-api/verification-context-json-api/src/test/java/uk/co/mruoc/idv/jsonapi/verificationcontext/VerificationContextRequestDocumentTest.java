package uk.co.mruoc.idv.jsonapi.verificationcontext;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.channel.model.DefaultChannel;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.json.JsonConverter;

import java.time.Instant;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class VerificationContextRequestDocumentTest {

    private static final String JSON = loadContentFromClasspath("/verification-context-request-document.json");
    private static final VerificationContextRequestDocument DOCUMENT = buildDocument();

    private static final JsonConverter CONVERTER = new JsonApiVerificationContextJsonConverterFactory().build();

    @Test
    public void shouldSerializeDocument() {
        final String json = CONVERTER.toJson(DOCUMENT);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeDocument() {
        final VerificationContextRequestDocument document = CONVERTER.toObject(JSON, VerificationContextRequestDocument.class);

        assertThat(document).isEqualToComparingFieldByFieldRecursively(DOCUMENT);
    }

    @Test
    public void shouldReturnAttributes() {
        final DefaultVerificationContextRequest request = buildRequest();

        final VerificationContextRequestDocument document = new VerificationContextRequestDocument(request);

        assertThat(document.getRequest()).isEqualTo(request);
    }

    private static VerificationContextRequestDocument buildDocument() {
        final DefaultVerificationContextRequest request = buildRequest();
        return new VerificationContextRequestDocument(request);
    }

    private static DefaultVerificationContextRequest buildRequest() {
        final Alias providedAlias = new TokenizedCreditCardNumberAlias("3489347343788005");
        return DefaultVerificationContextRequest.builder()
                .channel(new DefaultChannel("DEFAULT"))
                .activity(new LoginActivity(Instant.parse("2019-03-10T12:53:57.547Z")))
                .providedAlias(providedAlias)
                .build();
    }

}