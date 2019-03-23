package uk.co.mruoc.idv.core.identity.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.AliasType;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedDebitCardNumberAlias;

import java.util.Collection;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DefaultAliasLoaderTest {

    private static final String SUPPORTED_CHANNEL_ID = "CHANNEL_ID";
    private static final Collection<String> SUPPORTED_CHANNEL_IDS = singleton(SUPPORTED_CHANNEL_ID);
    private static final Collection<String> SUPPORTED_ALIAS_TYPES = singleton(AliasType.Names.CREDIT_CARD_NUMBER);

    private final Alias supportedAlias = new TokenizedCreditCardNumberAlias("22222222");
    private final Alias unsupportedAlias = new TokenizedDebitCardNumberAlias("9999999999");

    private final AliasHandler handler = mock(AliasHandler.class);

    private final AliasLoader loader = new DefaultAliasLoader(SUPPORTED_CHANNEL_IDS, SUPPORTED_ALIAS_TYPES, handler);

    @Test
    public void shouldReturnEmptyListIfChannelIdIsNotSupported() {
        final AliasLoaderRequest request = AliasLoaderRequest.builder()
                .channelId("UNSUPPORTED_CHANNEL_ID")
                .providedAlias(supportedAlias)
                .build();

        Aliases loadedAliases = loader.load(request);

        assertThat(loadedAliases).isEmpty();
    }

    @Test
    public void shouldReturnEmptyListIfAliasTypeIsNotSupported() {
        final AliasLoaderRequest request = AliasLoaderRequest.builder()
                .channelId(SUPPORTED_CHANNEL_ID)
                .providedAlias(unsupportedAlias)
                .build();

        Aliases loadedAliases = loader.load(request);

        assertThat(loadedAliases).isEmpty();
    }

    @Test
    public void shouldReturnLoadedAliasesIfChannelAndAliasTypeAreSupported() {
        final AliasLoaderRequest request = AliasLoaderRequest.builder()
                .channelId(SUPPORTED_CHANNEL_ID)
                .providedAlias(supportedAlias)
                .build();
        final Alias loadedAlias = new TokenizedCreditCardNumberAlias("1111111111");
        given(handler.loadAliases(supportedAlias)).willReturn(singleton(loadedAlias));

        Aliases loadedAliases = loader.load(request);

        assertThat(loadedAliases).containsExactly(loadedAlias);
    }

}
