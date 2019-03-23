package uk.co.mruoc.idv.plugin.uk.identity.aliasloader.rsa;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.AliasLoadFailedException;
import uk.co.mruoc.idv.core.identity.service.AliasLoader;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderRequest;
import uk.co.mruoc.idv.plugin.uk.identity.model.BukCustomerIdAlias;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class FakeRsaCreditCardNumberAliasLoaderTest {

    private static final String SUPPORTED_CHANNEL_ID = "RSA";

    private final Alias supportedAlias = new TokenizedCreditCardNumberAlias("4327432895438001");
    private final Alias unsupportedAlias = new BukCustomerIdAlias("9999999999");

    private final AliasLoader loader = new FakeRsaCreditCardNumberAliasLoader();

    @Test
    public void shouldReturnEmptyListIfChannelIdIsNotSupported() {
        final AliasLoaderRequest request = AliasLoaderRequest.builder()
                .channelId("UNSUPPORTED_CHANNEL_ID")
                .providedAlias(supportedAlias)
                .build();

        final Aliases loadedAliases = loader.load(request);

        assertThat(loadedAliases).isEmpty();
    }

    @Test
    public void shouldReturnEmptyListIfAliasTypeIsNotSupported() {
        final AliasLoaderRequest request = AliasLoaderRequest.builder()
                .channelId(SUPPORTED_CHANNEL_ID)
                .providedAlias(unsupportedAlias)
                .build();

        final Aliases loadedAliases = loader.load(request);

        assertThat(loadedAliases).isEmpty();
    }

    @Test
    public void shouldThrowExceptionIfAliasValueEndsWithNine() {
        final Alias failureAlias = new TokenizedCreditCardNumberAlias("4327432895438009");
        final AliasLoaderRequest request = AliasLoaderRequest.builder()
                .channelId(SUPPORTED_CHANNEL_ID)
                .providedAlias(failureAlias)
                .build();

        final Throwable exception = catchThrowable(() -> loader.load(request));

        assertThat(exception).isInstanceOf(AliasLoadFailedException.class)
                .hasMessageEndingWith(failureAlias.toString());
    }

    @Test
    public void shouldReturnHardcodedAliasIfChannelAndAliasTypeAreSupported() {
        final AliasLoaderRequest request = AliasLoaderRequest.builder()
                .channelId(SUPPORTED_CHANNEL_ID)
                .providedAlias(supportedAlias)
                .build();
        final Alias expectedAlias = new BukCustomerIdAlias("3333333333");

        Aliases loadedAliases = loader.load(request);

        assertThat(loadedAliases).containsExactly(expectedAlias);
    }

}
