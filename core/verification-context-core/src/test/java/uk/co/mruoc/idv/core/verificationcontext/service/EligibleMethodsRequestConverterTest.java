package uk.co.mruoc.idv.core.verificationcontext.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.EligibleMethodsRequest;
import uk.co.mruoc.idv.core.verificationcontext.model.channel.Channel;
import uk.co.mruoc.idv.core.verificationcontext.model.policy.VerificationMethodPolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class EligibleMethodsRequestConverterTest {

    private final VerificationMethodPolicy methodPolicy = mock(VerificationMethodPolicy.class);

    private final EligibleMethodsRequestConverter converter = new EligibleMethodsRequestConverter();

    @Test
    public void shouldPopulateChannel() {
        final Channel channel = mock(Channel.class);
        final EligibleMethodsRequest methodsRequest = mock(EligibleMethodsRequest.class);
        given(methodsRequest.getChannel()).willReturn(channel);

        final EligibleMethodRequest methodRequest = converter.toMethodRequest(methodsRequest, methodPolicy);

        assertThat(methodRequest.getChannel()).isEqualTo(channel);
    }

    @Test
    public void shouldPopulateIdentity() {
        final Identity identity = mock(Identity.class);
        final EligibleMethodsRequest methodsRequest = mock(EligibleMethodsRequest.class);
        given(methodsRequest.getIdentity()).willReturn(identity);

        final EligibleMethodRequest methodRequest = converter.toMethodRequest(methodsRequest, methodPolicy);

        assertThat(methodRequest.getIdentity()).isEqualTo(identity);
    }

    @Test
    public void shouldPopulateInputAlias() {
        final Alias inputAlias = mock(Alias.class);
        final EligibleMethodsRequest methodsRequest = mock(EligibleMethodsRequest.class);
        given(methodsRequest.getInputAlias()).willReturn(inputAlias);

        final EligibleMethodRequest methodRequest = converter.toMethodRequest(methodsRequest, methodPolicy);

        assertThat(methodRequest.getInputAlias()).isEqualTo(inputAlias);
    }

    @Test
    public void shouldPopulateMethodPolicy() {
        final EligibleMethodsRequest methodsRequest = mock(EligibleMethodsRequest.class);

        final EligibleMethodRequest methodRequest = converter.toMethodRequest(methodsRequest, methodPolicy);

        assertThat(methodRequest.getMethodPolicy()).isEqualTo(methodPolicy);
    }

}