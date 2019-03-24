package uk.co.mruoc.idv.core.identity.service;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.cardnumber.TokenizedCreditCardNumberAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityService.IdentityNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class IdentityServiceTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    private final IdentityDao dao = mock(IdentityDao.class);
    private final DefaultAliasLoaderService aliasLoaderService = mock(DefaultAliasLoaderService.class);
    private final IdvIdGenerator idvIdGenerator = mock(IdvIdGenerator.class);
    private final Alias alias = new TokenizedCreditCardNumberAlias("1234567890123456");

    private final IdentityService service = IdentityService.builder()
            .dao(dao)
            .aliasLoaderService(aliasLoaderService)
            .idvIdGenerator(idvIdGenerator)
            .build();

    @Test
    public void shouldThrowExceptionIfIdentityNotLoaded() {
        given(dao.load(alias)).willReturn(Optional.empty());

        final Throwable thrown = catchThrowable(() -> service.load(alias));

        assertThat(thrown)
                .isInstanceOf(IdentityNotFoundException.class)
                .hasMessage(alias.toString());
    }

    @Test
    public void shouldReturnLoadedIdentity() {
        final Alias idvId = new IdvIdAlias();
        final Identity loadedIdentity = Identity.withAliases(idvId, alias);
        given(dao.load(alias)).willReturn(Optional.of(loadedIdentity));

        final Identity identity = service.load(alias);

        assertThat(identity).isEqualTo(loadedIdentity);
    }

    @Test
    public void shouldCreateNewIdentityIfIdentityDoesNotAlreadyExist() {
        given(dao.load(alias)).willReturn(Optional.empty());
        final IdvIdAlias idvId = new IdvIdAlias();
        given(idvIdGenerator.generate()).willReturn(idvId);
        given(aliasLoaderService.loadAliases(any(AliasLoaderRequest.class))).willReturn(Aliases.empty());
        final UpsertIdentityRequest request = UpsertIdentityRequest.builder()
                .channelId(CHANNEL_ID)
                .providedAlias(alias)
                .build();

        final Identity identity = service.upsert(request);

        assertThat(identity.getAliases()).containsExactlyInAnyOrder(idvId, alias);
        verify(dao).save(identity);
    }

    @Test
    public void shouldPassChannelIdAndProvidedAliasToAliasLoaderWhenNewIdentityCreated() {
        given(dao.load(alias)).willReturn(Optional.empty());
        final IdvIdAlias idvId = new IdvIdAlias();
        given(idvIdGenerator.generate()).willReturn(idvId);
        given(aliasLoaderService.loadAliases(any(AliasLoaderRequest.class))).willReturn(Aliases.empty());
        final UpsertIdentityRequest request = UpsertIdentityRequest.builder()
                .channelId(CHANNEL_ID)
                .providedAlias(alias)
                .build();
        ArgumentCaptor<AliasLoaderRequest> captor = ArgumentCaptor.forClass(AliasLoaderRequest.class);

        service.upsert(request);

        verify(aliasLoaderService).loadAliases(captor.capture());
        assertThat(captor.getValue().getChannelId()).isEqualTo(CHANNEL_ID);
        assertThat(captor.getValue().getProvidedAlias()).isEqualTo(alias);
    }

    @Test
    public void shouldLoadIdentityIfIdentityAlreadyExists() {
        final IdvIdAlias idvId = new IdvIdAlias();
        final Identity existingIdentity = Identity.withAliases(idvId, alias);
        given(dao.load(alias)).willReturn(Optional.of(existingIdentity));
        final UpsertIdentityRequest request = UpsertIdentityRequest.builder()
                .channelId(CHANNEL_ID)
                .providedAlias(alias)
                .build();

        final Identity identity = service.upsert(request);

        assertThat(identity).isEqualTo(existingIdentity);
    }

    @Test
    public void shouldNotCallAliasLoaderOrSaveIdentityWhenExistingIdentityLoaded() {
        final IdvIdAlias idvId = new IdvIdAlias();
        given(dao.load(alias)).willReturn(Optional.of(Identity.withAliases(idvId, alias)));
        final UpsertIdentityRequest request = UpsertIdentityRequest.builder()
                .channelId(CHANNEL_ID)
                .providedAlias(alias)
                .build();

        service.upsert(request);

        verify(aliasLoaderService, never()).loadAliases(any(AliasLoaderRequest.class));
        verify(dao, never()).save(any(Identity.class));
    }

}
