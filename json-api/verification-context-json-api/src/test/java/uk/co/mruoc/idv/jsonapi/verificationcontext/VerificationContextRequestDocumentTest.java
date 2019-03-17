package uk.co.mruoc.idv.jsonapi.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.verificationcontext.model.activity.LoginActivity;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.DefaultChannel;

import java.io.IOException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationContextRequestDocumentTest {

    private static final String JSON = JsonLoader.loadJson("/verification-context-request-document.json");
    private static final VerificationContextRequestDocument DOCUMENT = buildDocument();
    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    public void shouldSerializeDocument() throws JsonProcessingException {
        final String json = MAPPER.writeValueAsString(DOCUMENT);

        assertThat(json).isEqualTo(JSON);
    }

    @Test
    public void shouldDeserializeDocument() throws IOException {
        final VerificationContextRequestDocument document = MAPPER.readValue(JSON, VerificationContextRequestDocument.class);

        assertThat(document).isEqualToComparingFieldByFieldRecursively(DOCUMENT);
    }

    @Test
    public void shouldReturnAttributes() {
        final VerificationContextRequest request = buildRequest();

        final VerificationContextRequestDocument document = new VerificationContextRequestDocument(request);

        assertThat(document.getRequest()).isEqualTo(request);
    }

    private static VerificationContextRequestDocument buildDocument() {
        final VerificationContextRequest request = buildRequest();
        return new VerificationContextRequestDocument(request);
    }

    private static VerificationContextRequest buildRequest() {
        final Alias providedAlias = new TokenizedCreditCardNumberAlias("3489347343788005");
        return VerificationContextRequest.builder()
                .channel(new DefaultChannel("DEFAULT"))
                .activity(new LoginActivity(Instant.parse("2019-03-10T12:53:57.547Z")))
                .providedAlias(providedAlias)
                .build();
    }

}