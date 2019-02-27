package uk.co.mruoc.idv.core.identity.service;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.idv.core.identity.model.Identity;
import uk.co.mruoc.idv.core.identity.model.alias.Alias;
import uk.co.mruoc.idv.core.identity.model.alias.Aliases;
import uk.co.mruoc.idv.core.identity.model.alias.BukCustomerIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.IdvIdAlias;
import uk.co.mruoc.idv.core.identity.model.alias.UkcCardholderIdAlias;
import uk.co.mruoc.idv.core.identity.service.IdentityService.IdentityNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class IdentityServiceTest {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    private final IdentityDao dao = mock(IdentityDao.class);
    private final AliasLoaderService aliasLoaderService = mock(AliasLoaderService.class);
    private final IdvIdGenerator idvIdGenerator = mock(IdvIdGenerator.class);

    private final IdentityService service = new IdentityService(dao, aliasLoaderService, idvIdGenerator);

    @Test
    public void shouldThrowExceptionIfIdentityNotLoaded() {
        final Alias alias = new UkcCardholderIdAlias("12345678");
        given(dao.load(alias)).willReturn(Optional.empty());

        final Throwable thrown = catchThrowable(() -> service.load(alias));

        assertThat(thrown)
                .isInstanceOf(IdentityNotFoundException.class)
                .hasMessage(alias.toString());
    }

    @Test
    public void shouldReturnLoadedIdentity() {
        final Alias idvId = new IdvIdAlias();
        final Alias cardholderId = new UkcCardholderIdAlias("12345678");
        final Identity loadedIdentity = Identity.withAliases(idvId, cardholderId);
        given(dao.load(cardholderId)).willReturn(Optional.of(loadedIdentity));

        final Identity identity = service.load(cardholderId);

        assertThat(identity).isEqualTo(loadedIdentity);
    }

    @Test
    public void shouldCreateNewIdentityIfIdentityDoesNotAlreadyExist() {
        final Alias cardholderId = new UkcCardholderIdAlias("12345678");
        given(dao.load(cardholderId)).willReturn(Optional.empty());
        final IdvIdAlias idvId = new IdvIdAlias();
        given(idvIdGenerator.generate()).willReturn(idvId);
        given(aliasLoaderService.loadAliases(any(AliasLoaderRequest.class))).willReturn(Aliases.empty());
        final UpsertIdentityRequest request = UpsertIdentityRequest.builder()
                .channelId(CHANNEL_ID)
                .alias(cardholderId)
                .build();

        final Identity identity = service.upsert(request);

        assertThat(identity.getAliases()).containsExactlyInAnyOrder(idvId, cardholderId);
        verify(dao).save(identity);
    }

    @Test
    public void shouldLoadIdentityIfIdentityAlreadyExists() {
        final IdvIdAlias idvId = new IdvIdAlias();
        final Alias bukCustomerId = new BukCustomerIdAlias("12345678");
        given(dao.load(bukCustomerId)).willReturn(Optional.of(Identity.withAliases(idvId, bukCustomerId)));
        given(aliasLoaderService.loadAliases(any(AliasLoaderRequest.class))).willReturn(Aliases.empty());
        final UpsertIdentityRequest request = UpsertIdentityRequest.builder()
                .channelId(CHANNEL_ID)
                .alias(bukCustomerId)
                .build();

        final Identity identity = service.upsert(request);

        assertThat(identity.getAliases()).containsExactlyInAnyOrder(idvId, bukCustomerId);
        verify(dao).save(identity);
    }

    @Test
    public void shouldPassChannelIdAndAliasesToAliasLoaderWhenNewIdentityCreated() {
        final Alias cardholderId = new UkcCardholderIdAlias("12345678");
        given(dao.load(cardholderId)).willReturn(Optional.empty());
        final IdvIdAlias idvId = new IdvIdAlias();
        given(idvIdGenerator.generate()).willReturn(idvId);
        given(aliasLoaderService.loadAliases(any(AliasLoaderRequest.class))).willReturn(Aliases.empty());
        final UpsertIdentityRequest request = UpsertIdentityRequest.builder()
                .channelId(CHANNEL_ID)
                .alias(cardholderId)
                .build();
        ArgumentCaptor<AliasLoaderRequest> captor = ArgumentCaptor.forClass(AliasLoaderRequest.class);

        service.upsert(request);

        verify(aliasLoaderService).loadAliases(captor.capture());
        assertThat(captor.getValue().getChannelId()).isEqualTo(CHANNEL_ID);
        assertThat(captor.getValue().getAliases()).containsExactlyInAnyOrder(idvId, cardholderId);
    }

    @Test
    public void shouldPassChannelIdAndAliasesToAliasLoaderWhenExistingIdentityLoaded() {
        final IdvIdAlias idvId = new IdvIdAlias();
        final Alias bukCustomerId = new BukCustomerIdAlias("12345678");
        given(dao.load(bukCustomerId)).willReturn(Optional.of(Identity.withAliases(idvId, bukCustomerId)));
        given(aliasLoaderService.loadAliases(any(AliasLoaderRequest.class))).willReturn(Aliases.empty());
        final UpsertIdentityRequest request = UpsertIdentityRequest.builder()
                .channelId(CHANNEL_ID)
                .alias(bukCustomerId)
                .build();
        ArgumentCaptor<AliasLoaderRequest> captor = ArgumentCaptor.forClass(AliasLoaderRequest.class);

        service.upsert(request);

        verify(aliasLoaderService).loadAliases(captor.capture());
        assertThat(captor.getValue().getChannelId()).isEqualTo(CHANNEL_ID);
        assertThat(captor.getValue().getAliases()).containsExactlyInAnyOrder(idvId, bukCustomerId);
    }

}
