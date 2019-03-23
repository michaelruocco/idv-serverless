package uk.co.mruoc.idv.core.identity.service;

import org.junit.Test;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedDebitCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.AliasLoaderService.AliasTypeNotSupportedException;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class AliasLoaderServiceTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    private final Alias providedAlias = new TokenizedCreditCardNumberAlias("1234567890123456");
    private final AliasLoaderRequest request = new AliasLoaderRequest(CHANNEL_ID, providedAlias);

    private final AliasLoader loader1 = mock(AliasLoader.class);
    private final AliasLoader loader2 = mock(AliasLoader.class);
    private final AliasLoaderService service = new AliasLoaderService(Arrays.asList(loader1, loader2));

    @Test
    public void shouldThrowExceptionIfAliasIsNotSupportedByAnyAliasLoaders() {
        final Throwable cause = catchThrowable(() -> service.loadAliases(request));

        assertThat(cause).isInstanceOf(AliasTypeNotSupportedException.class)
                .hasMessage("alias type CREDIT_CARD_NUMBER is not supported for channel CHANNEL_ID");
    }

    @Test
    public void shouldReturnProvidedAliasWithLoadedAliases() {
        final Alias loadedAlias1 = new TokenizedCreditCardNumberAlias("2222222222222222");
        final Alias loadedAlias2 = new TokenizedDebitCardNumberAlias("0987654321654321");

        given(loader1.supports(request)).willReturn(true);
        given(loader1.load(request)).willReturn(Aliases.with(loadedAlias1));

        given(loader2.supports(request)).willReturn(true);
        given(loader2.load(request)).willReturn(Aliases.with(loadedAlias2));

        final Aliases aliases = service.loadAliases(request);

        final Aliases expectedAliases = Aliases.with(loadedAlias1, loadedAlias2).add(providedAlias);
        assertThat(aliases).containsExactlyInAnyOrderElementsOf(expectedAliases);
    }

    @Test
    public void shouldReturnOnlyProvidedAliasIfLoadersDoNotReturnLoadedAliases() {
        given(loader1.supports(request)).willReturn(true);
        given(loader1.load(request)).willReturn(Aliases.empty());

        given(loader2.supports(request)).willReturn(true);
        given(loader2.load(request)).willReturn(Aliases.empty());

        final Aliases aliases = service.loadAliases(request);

        assertThat(aliases).containsExactly(providedAlias);
    }

    @Test
    public void shouldThrowAliasLoadFailedExceptionIfExceptionThrownFromLoader() {
        final Throwable error = new AliasLoadFailedException(mock(Alias.class));
        given(loader1.supports(request)).willReturn(true);
        given(loader1.load(request)).willThrow(error);

        final Throwable thrown = catchThrowable(() -> service.loadAliases(request));

        assertThat(thrown).isEqualTo(error);
    }

}
