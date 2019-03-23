package uk.co.mruoc.idv.plugin.identity.aliasloader.as3;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.model.alias.BukCustomerIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAlias;
import uk.co.mruoc.idv.core.identity.service.AliasLoadFailedException;
import uk.co.mruoc.idv.core.identity.service.AliasLoader;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class FakeAs3UkcCardholderIdAliasTypeAliasLoaderTest {

    private static final String SUPPORTED_CHANNEL_ID = "AS3";

    private final Alias supportedAlias = new UkcCardholderIdAlias("22222222");
    private final Alias unsupportedAlias = new BukCustomerIdAlias("9999999999");

    private final AliasLoader loader = new FakeAs3UkcCardholderIdAliasLoader();

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
        final Alias failureAlias = new UkcCardholderIdAlias("22222229");
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
        final Alias expectedAlias = new BukCustomerIdAlias("1212121212");

        final Aliases loadedAliases = loader.load(request);

        assertThat(loadedAliases).containsExactly(expectedAlias);
    }

}
