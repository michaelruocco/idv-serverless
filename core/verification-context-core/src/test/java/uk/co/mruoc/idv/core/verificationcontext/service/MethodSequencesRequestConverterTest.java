package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.verificationcontext.model.VerificationMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.MethodSequencesRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MethodSequencesRequestConverterTest {

    private final VerificationMethodPolicy methodPolicy = mock(VerificationMethodPolicy.class);

    private final VerificationMethodsRequestConverter converter = new VerificationMethodsRequestConverter();

    @Test
    public void shouldPopulateChannel() {
        final Channel channel = mock(Channel.class);
        final MethodSequencesRequest methodsRequest = mock(MethodSequencesRequest.class);
        given(methodsRequest.getChannel()).willReturn(channel);

        final VerificationMethodRequest methodRequest = converter.toMethodRequest(methodsRequest, methodPolicy);

        assertThat(methodRequest.getChannel()).isEqualTo(channel);
    }

    @Test
    public void shouldPopulateIdentity() {
        final Identity identity = mock(Identity.class);
        final MethodSequencesRequest methodsRequest = mock(MethodSequencesRequest.class);
        given(methodsRequest.getIdentity()).willReturn(identity);

        final VerificationMethodRequest methodRequest = converter.toMethodRequest(methodsRequest, methodPolicy);

        assertThat(methodRequest.getIdentity()).isEqualTo(identity);
    }

    @Test
    public void shouldPopulateInputAlias() {
        final Alias inputAlias = mock(Alias.class);
        final MethodSequencesRequest methodsRequest = mock(MethodSequencesRequest.class);
        given(methodsRequest.getInputAlias()).willReturn(inputAlias);

        final VerificationMethodRequest methodRequest = converter.toMethodRequest(methodsRequest, methodPolicy);

        assertThat(methodRequest.getInputAlias()).isEqualTo(inputAlias);
    }

    @Test
    public void shouldPopulateMethodPolicy() {
        final MethodSequencesRequest methodsRequest = mock(MethodSequencesRequest.class);

        final VerificationMethodRequest methodRequest = converter.toMethodRequest(methodsRequest, methodPolicy);

        assertThat(methodRequest.getMethodPolicy()).isEqualTo(methodPolicy);
    }

}