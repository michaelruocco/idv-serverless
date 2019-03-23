package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.service.UpsertIdentityRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationContextRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class VerificationContextRequestConverterTest {

    private final VerificationContextRequestConverter converter = new VerificationContextRequestConverter();

    @Test
    public void shouldConvertProvidedAlias() {
        final Alias providedAlias = mock(Alias.class);
        final VerificationContextRequest contextRequest = mock(VerificationContextRequest.class);
        given(contextRequest.getProvidedAlias()).willReturn(providedAlias);

        final UpsertIdentityRequest upsertRequest = converter.toUpsertIdentityRequest(contextRequest);

        assertThat(upsertRequest.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    public void shouldConvertChannelId() {
        final String channelId = "CHANNEL_ID";
        final VerificationContextRequest contextRequest = mock(VerificationContextRequest.class);
        given(contextRequest.getChannelId()).willReturn(channelId);

        final UpsertIdentityRequest upsertRequest = converter.toUpsertIdentityRequest(contextRequest);

        assertThat(upsertRequest.getChannelId()).isEqualTo(channelId);
    }
}
